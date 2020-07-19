package com.rookia.android.androidutils.framework.repository

import com.rookia.android.androidutils.domain.vo.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

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
@ExperimentalCoroutinesApi
class SingleSourceOfTruthStrategyKtTest {


//    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    interface SingleSourceOfTruthPersistenceAndNetworkTestClass {
        fun databaseQuery(): List<Int>
        suspend fun networkCall(): Result<List<Int>>
        suspend fun saveCallResult(value: Int?)
    }

    interface SingleSourceOfTruthOnlyNetworkTestClass {
        suspend fun networkCall(): Result<List<Int>>
    }

    private val responseFromDb: List<Int> = listOf(1, 2, 3)
    private val responseFromNetwork: List<Int> = listOf(4, 5, 6)
    private var databaseUpdatedWithNetwork = false

    @RelaxedMockK
    lateinit var singleSourceOfTruthTestClassPersistenceAndNetwork: SingleSourceOfTruthPersistenceAndNetworkTestClass

    @RelaxedMockK
    lateinit var singleSourceOfTruthOnlyNetworkFlowTestClass: SingleSourceOfTruthOnlyNetworkTestClass

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
//        Dispatchers.setMain(mainThreadSurrogate)
        databaseUpdatedWithNetwork = false
        every { singleSourceOfTruthTestClassPersistenceAndNetwork.databaseQuery() } answers {
            if (databaseUpdatedWithNetwork) {
                responseFromNetwork
            } else {
                responseFromDb
            }
        }
        coEvery { singleSourceOfTruthTestClassPersistenceAndNetwork.saveCallResult(any()) } coAnswers {
            databaseUpdatedWithNetwork = true
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
//        mainThreadSurrogate.close()
    }

    @Test
    fun `don't call server if runNetworkCall is false in get result NO Flow`() {
        runBlockingTest {
            val response = resultFromPersistenceAndNetwork(
                { singleSourceOfTruthTestClassPersistenceAndNetwork.databaseQuery() },
                { singleSourceOfTruthTestClassPersistenceAndNetwork.networkCall() },
                { singleSourceOfTruthTestClassPersistenceAndNetwork.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataNotOutdated() }
            )

            Assert.assertEquals(responseFromDb, response)

        }
    }

    @Test
    fun `call server if runNetworkCall is true in get result NO Flow, with success response`() {
        coEvery { singleSourceOfTruthTestClassPersistenceAndNetwork.networkCall() } returns Result.success(
            responseFromNetwork
        )
        runBlockingTest {
            val response = resultFromPersistenceAndNetwork(
                { singleSourceOfTruthTestClassPersistenceAndNetwork.databaseQuery() },
                { singleSourceOfTruthTestClassPersistenceAndNetwork.networkCall() },
                { singleSourceOfTruthTestClassPersistenceAndNetwork.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataOutdated() }
            )

            Assert.assertEquals(responseFromNetwork, response)
        }
    }

    @Test
    fun `call server if runNetworkCall is true in get result NO Flow, with error response`() {
        val errorMessage = "error message"
        coEvery { singleSourceOfTruthTestClassPersistenceAndNetwork.networkCall() } returns Result.error(
            errorMessage,
            null
        )
        runBlockingTest {
            val response = resultFromPersistenceAndNetwork(
                { singleSourceOfTruthTestClassPersistenceAndNetwork.databaseQuery() },
                { singleSourceOfTruthTestClassPersistenceAndNetwork.networkCall() },
                { singleSourceOfTruthTestClassPersistenceAndNetwork.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataOutdated() }
            )

            Assert.assertEquals(responseFromDb, response)
        }
    }

    @Test
    fun `don't call server if runNetworkCall is false in get result as Flow`() {
        runBlockingTest {
            val testFlow = resultFromPersistenceAndNetworkInFlow(
                { singleSourceOfTruthTestClassPersistenceAndNetwork.databaseQuery() },
                { singleSourceOfTruthTestClassPersistenceAndNetwork.networkCall() },
                { singleSourceOfTruthTestClassPersistenceAndNetwork.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataNotOutdated() }
            )
            val list = mutableListOf<Result<List<Int>>>()
            testFlow.toList(list)

            assert(list.size == 1)
            assert(list.first() == Result.success(responseFromDb))
        }
    }

    @Test
    fun `call server if runNetworkCall is true in get result as Flow, with success response`() {
        coEvery { singleSourceOfTruthTestClassPersistenceAndNetwork.networkCall() } returns Result.success(
            responseFromNetwork
        )
        runBlocking(Dispatchers.IO) {
            val testFlow = resultFromPersistenceAndNetworkInFlow(
                { singleSourceOfTruthTestClassPersistenceAndNetwork.databaseQuery() },
                { singleSourceOfTruthTestClassPersistenceAndNetwork.networkCall() },
                { singleSourceOfTruthTestClassPersistenceAndNetwork.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataOutdated() }
            )

            val list = mutableListOf<Result<List<Int>>>()
            testFlow.toList(list)

            assert(list.size == 2)
            assert(list.first() == Result.loading(responseFromDb))
            assert(list.last() == Result.success(responseFromNetwork))
        }
    }

    @Test
    fun `call server if runNetworkCall is true in get result as Flow, with error response`() {
        val errorMessage = "error message"
        coEvery { singleSourceOfTruthTestClassPersistenceAndNetwork.networkCall() } returns Result.error(
            errorMessage,
            null
        )
        runBlocking(Dispatchers.IO) {
            val testFlow = resultFromPersistenceAndNetworkInFlow(
                { singleSourceOfTruthTestClassPersistenceAndNetwork.databaseQuery() },
                { singleSourceOfTruthTestClassPersistenceAndNetwork.networkCall() },
                { singleSourceOfTruthTestClassPersistenceAndNetwork.saveCallResult(any()) },
                isThePersistedInfoOutdated = { dataOutdated() }
            )
            val list = mutableListOf<Result<List<Int>>>()
            testFlow.toList(list)
            assert(list.size == 2)
            assert(list.first() == Result.loading(responseFromDb))
            assert(list.last() == Result.error(errorMessage, responseFromDb))
        }
    }


    @Test
    fun `call only server with get result as Flow, with success response`() {
        coEvery { singleSourceOfTruthOnlyNetworkFlowTestClass.networkCall() } returns Result.success(
            responseFromNetwork
        )
        runBlockingTest {
            val testFlow = resultOnlyFromOneSourceInFlow {
                singleSourceOfTruthOnlyNetworkFlowTestClass.networkCall()
            }

            val list = mutableListOf<Result<List<Int>>>()
            testFlow.toList(list)

            assert(list.size == 2)
            assert(list.first() == Result.loading(null))
            assert(list.last() == Result.success(responseFromNetwork))
        }
    }

    @InternalCoroutinesApi
    @Test
    fun `call only server with get result as Flow, with error response`() {
        val errorMessage = "error message"
        coEvery { singleSourceOfTruthOnlyNetworkFlowTestClass.networkCall() } returns Result.error(
            errorMessage,
            null
        )
        runBlockingTest {
            val testFlow = resultOnlyFromOneSourceInFlow {
                singleSourceOfTruthOnlyNetworkFlowTestClass.networkCall()
            }

            val list = mutableListOf<Result<List<Int>>>()
            testFlow.toList(list)

            assert(list.size == 2)
            assert(list.first() == Result.loading(null))
            assert(list.last() == Result.error(errorMessage, null))
        }
    }

    private fun dataOutdated(): Boolean = true
    private fun dataNotOutdated(): Boolean = false
}