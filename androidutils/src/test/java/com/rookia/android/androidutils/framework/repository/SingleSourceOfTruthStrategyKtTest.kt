package com.rookia.android.androidutils.framework.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jraska.livedata.test
import com.rookia.android.androidutils.domain.vo.Result
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.ArgumentMatchers.any
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 *
 * Written by Roll <raulfeliz></raulfeliz>@gmail.com>, April 2020
 */

@Suppress("UNCHECKED_CAST")
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class SingleSourceOfTruthStrategyKtTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    interface SingleSourceOfTruthObservableTestClass {
        fun databaseQuery(): LiveData<List<Int>>
        suspend fun networkCall(): Result<Int>
        suspend fun saveCallResult(value: Int?)
    }

    interface SingleSourceOfTruthTestClass {
        fun databaseQuery(): List<Int>
        suspend fun networkCall(): Result<Int>
        suspend fun saveCallResult(value: Int?)
    }

    private lateinit var responseFromDb: List<Int>
    private lateinit var responseFromNetwork: List<Int>
    @RelaxedMockK
    lateinit var singleSourceOfTruthTestClassObservable: SingleSourceOfTruthObservableTestClass
    @RelaxedMockK
    lateinit var singleSourceOfTruthTestClassNoObservable: SingleSourceOfTruthTestClass

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        responseFromDb = listOf(1, 2, 3)
        responseFromNetwork = listOf(4, 5, 6)
        every { singleSourceOfTruthTestClassObservable.databaseQuery() } answers {
            MutableLiveData(responseFromDb)
        }
        coEvery { singleSourceOfTruthTestClassObservable.saveCallResult(any()) } coAnswers {
            responseFromDb = responseFromNetwork
        }

        every { singleSourceOfTruthTestClassNoObservable.databaseQuery() } answers {
            responseFromDb
        }
        coEvery { singleSourceOfTruthTestClassNoObservable.saveCallResult(any()) } coAnswers {
            responseFromDb = responseFromNetwork
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `don't call server if runNetworkCall is false in get result NO Observable`() {
        runBlocking(Dispatchers.IO) {
            val response = resultFromPersistenceAndNetwork(
                { singleSourceOfTruthTestClassNoObservable.databaseQuery() },
                { singleSourceOfTruthTestClassNoObservable.networkCall() },
                { singleSourceOfTruthTestClassNoObservable.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataNotOutdated(listOf()) }
            )

            Assert.assertEquals(responseFromDb, response)

        }
    }

    @Test
    fun `call server if runNetworkCall is true in get result NO Observable, with success response`() {
        coEvery { singleSourceOfTruthTestClassNoObservable.networkCall() } returns Result.success(8)
        runBlocking(Dispatchers.IO) {
            val response = resultFromPersistenceAndNetwork(
                { singleSourceOfTruthTestClassNoObservable.databaseQuery() },
                { singleSourceOfTruthTestClassNoObservable.networkCall() },
                { singleSourceOfTruthTestClassNoObservable.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataOutdated(listOf()) }
            )

            Assert.assertEquals(responseFromNetwork, response)
        }
    }

    @Test
    fun `call server if runNetworkCall is true in get result NO Observable, with error response`() {
        val errorMessage = "error message"
        coEvery { singleSourceOfTruthTestClassNoObservable.networkCall() } returns Result.error(
            errorMessage,
            8
        )
        runBlocking(Dispatchers.IO) {
            val response = resultFromPersistenceAndNetwork(
                { singleSourceOfTruthTestClassNoObservable.databaseQuery() },
                { singleSourceOfTruthTestClassNoObservable.networkCall() },
                { singleSourceOfTruthTestClassNoObservable.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataOutdated(listOf()) }
            )

            Assert.assertEquals(responseFromDb, response)
        }
    }

    @Test
    fun `don't call server if runNetworkCall is false in get result as Observable`() {
        runBlocking(Dispatchers.IO) {
            val testLiveData = resultFromPersistenceAndNetworkInLiveData(
                { singleSourceOfTruthTestClassObservable.databaseQuery() },
                { singleSourceOfTruthTestClassObservable.networkCall() },
                { singleSourceOfTruthTestClassObservable.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataNotOutdated(listOf()) }
            )

            val testObserver = testLiveData.test()
            val latch = CountDownLatch(1)
            val observer = Observer<Result<List<Any>>> {
                latch.countDown()
            }
            testLiveData.observeForever(observer)
            latch.await(10, TimeUnit.SECONDS)
            testObserver
                .assertHasValue()
                .assertHistorySize(1)
                .assertValue(Result.success(responseFromDb))
        }
    }

    @Test
    fun `call server if runNetworkCall is true in get result as Observable, with success response`() {
        coEvery { singleSourceOfTruthTestClassObservable.networkCall() } returns Result.success(8)
        runBlocking(Dispatchers.IO) {
            val initialDbResponse = responseFromDb
            val testLiveData = resultFromPersistenceAndNetworkInLiveData(
                { singleSourceOfTruthTestClassObservable.databaseQuery() },
                { singleSourceOfTruthTestClassObservable.networkCall() },
                { singleSourceOfTruthTestClassObservable.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataOutdated(listOf()) }
            )

            val testObserver = testLiveData.test()
            val latch = CountDownLatch(2)
            val observer = Observer<Result<List<Any>>> {
                latch.countDown()
            }
            testLiveData.observeForever(observer)
            latch.await(10, TimeUnit.SECONDS)
            testObserver
                .assertHasValue()
                .assertHistorySize(2)
                .assertValue(Result.success(responseFromNetwork))
                .assertValueHistory(Result.loading(initialDbResponse), Result.success(responseFromNetwork))
        }
    }

    @Test
    fun `call server if runNetworkCall is true in get result as Observable, with error response`() {
        val errorMessage = "error message"
        coEvery { singleSourceOfTruthTestClassObservable.networkCall() } returns Result.error(
            errorMessage,
            8
        )
        runBlocking(Dispatchers.IO) {
            val testLiveData = resultFromPersistenceAndNetworkInLiveData(
                { singleSourceOfTruthTestClassObservable.databaseQuery() },
                { singleSourceOfTruthTestClassObservable.networkCall() },
                { singleSourceOfTruthTestClassObservable.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataOutdated(listOf()) }
            )

            val testObserver = testLiveData.test()
            val latch = CountDownLatch(2)
            val observer = Observer<Result<List<Any>>> {
                latch.countDown()
            }
            testLiveData.observeForever(observer)
            latch.await(10, TimeUnit.SECONDS)
            testObserver
                .assertHasValue()
                .assertHistorySize(2)
                .assertValue(Result.error(errorMessage, responseFromDb))
                .assertValueHistory(Result.loading(responseFromDb), Result.error(errorMessage, responseFromDb))
        }
    }

    private fun dataOutdated(list: List<Any>): Boolean = true
    private fun dataNotOutdated(list: List<Any>): Boolean = false
    private fun getPersistenceData(): List<Int> = responseFromDb
}