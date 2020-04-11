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

    interface SingleSourceOfTruthPersistenceAndNetworkObservableTestClass {
        fun databaseQuery(): LiveData<List<Int>>
        suspend fun networkCall(): Result<List<Int>>
        suspend fun saveCallResult(value: Int?)
    }

    interface SingleSourceOfTruthPersistenceAndNetworkTestClass {
        fun databaseQuery(): List<Int>
        suspend fun networkCall(): Result<List<Int>>
        suspend fun saveCallResult(value: Int?)
    }

    interface SingleSourceOfTruthOnlyNetworkObservableTestClass {
        suspend fun networkCall(): Result<List<Int>>
    }

    private val responseFromDb: List<Int> = listOf(1, 2, 3)
    private val responseFromNetwork: List<Int> = listOf(4, 5, 6)
    private var databaseUpdatedWithNetwork = false

    @RelaxedMockK
    lateinit var singleSourceOfTruthTestClassPersistenceAndNetworkObservable: SingleSourceOfTruthPersistenceAndNetworkObservableTestClass
    @RelaxedMockK
    lateinit var singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable: SingleSourceOfTruthPersistenceAndNetworkTestClass
    @RelaxedMockK
    lateinit var singleSourceOfTruthOnlyNetworkObservableTestClass: SingleSourceOfTruthOnlyNetworkObservableTestClass

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        databaseUpdatedWithNetwork = false
        every { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.databaseQuery() } answers {
            if(databaseUpdatedWithNetwork){
                MutableLiveData(responseFromNetwork)
            } else {
                MutableLiveData(responseFromDb)
            }
        }
        coEvery { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.saveCallResult(any()) } coAnswers {
            databaseUpdatedWithNetwork = true
        }

        every { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.databaseQuery() } answers {
            if(databaseUpdatedWithNetwork){
                responseFromNetwork
            } else {
                responseFromDb
            }
        }
        coEvery { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.saveCallResult(any()) } coAnswers {
            databaseUpdatedWithNetwork = true
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
                { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.databaseQuery() },
                { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.networkCall() },
                { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataNotOutdated(listOf()) }
            )

            Assert.assertEquals(responseFromDb, response)

        }
    }

    @Test
    fun `call server if runNetworkCall is true in get result NO Observable, with success response`() {
        coEvery { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.networkCall() } returns Result.success(responseFromNetwork)
        runBlocking(Dispatchers.IO) {
            val response = resultFromPersistenceAndNetwork(
                { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.databaseQuery() },
                { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.networkCall() },
                { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataOutdated(listOf()) }
            )

            Assert.assertEquals(responseFromNetwork, response)
        }
    }

    @Test
    fun `call server if runNetworkCall is true in get result NO Observable, with error response`() {
        val errorMessage = "error message"
        coEvery { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.networkCall() } returns Result.error(
            errorMessage,
            null
        )
        runBlocking(Dispatchers.IO) {
            val response = resultFromPersistenceAndNetwork(
                { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.databaseQuery() },
                { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.networkCall() },
                { singleSourceOfTruthPersistenceAndNetworkTestClassNoObservable.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataOutdated(listOf()) }
            )

            Assert.assertEquals(responseFromDb, response)
        }
    }

    @Test
    fun `don't call server if runNetworkCall is false in get result as Observable`() {
        runBlocking(Dispatchers.IO) {
            val testLiveData = resultFromPersistenceAndNetworkInLiveData(
                { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.databaseQuery() },
                { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.networkCall() },
                { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.saveCallResult(any()) },
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
        coEvery { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.networkCall() } returns Result.success(responseFromNetwork)
        runBlocking(Dispatchers.IO) {
            val initialDbResponse = responseFromDb
            val testLiveData = resultFromPersistenceAndNetworkInLiveData(
                { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.databaseQuery() },
                { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.networkCall() },
                { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.saveCallResult(any()) },
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
        coEvery { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.networkCall() } returns Result.error(
            errorMessage,
            null
        )
        runBlocking(Dispatchers.IO) {
            val testLiveData = resultFromPersistenceAndNetworkInLiveData(
                { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.databaseQuery() },
                { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.networkCall() },
                { singleSourceOfTruthTestClassPersistenceAndNetworkObservable.saveCallResult(any()) },
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

    @Test
    fun `call only server with get result as Observable, with success response`() {
        coEvery { singleSourceOfTruthOnlyNetworkObservableTestClass.networkCall() } returns Result.success(responseFromNetwork)
        runBlocking(Dispatchers.IO) {
            val testLiveData = resultOnlyFromNetworkInLiveData{
                singleSourceOfTruthOnlyNetworkObservableTestClass.networkCall()
            }

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
                .assertValueHistory(Result.loading(null), Result.success(responseFromNetwork))
        }
    }

    @Test
    fun `call only server with get result as Observable, with error response`() {
        val errorMessage = "error message"
        coEvery { singleSourceOfTruthOnlyNetworkObservableTestClass.networkCall() } returns Result.error(
            errorMessage,
            null
        )
        runBlocking(Dispatchers.IO) {
            val testLiveData = resultOnlyFromNetworkInLiveData{
                singleSourceOfTruthOnlyNetworkObservableTestClass.networkCall()
            }

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
                .assertValue(Result.error(errorMessage, null))
                .assertValueHistory(Result.loading(null), Result.error(errorMessage, null))
        }
    }

    private fun dataOutdated(list: List<Any>): Boolean = true
    private fun dataNotOutdated(list: List<Any>): Boolean = false
}