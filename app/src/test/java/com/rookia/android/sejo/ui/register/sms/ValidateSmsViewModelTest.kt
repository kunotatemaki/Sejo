package com.rookia.android.sejo.ui.register.sms

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jraska.livedata.test
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.domain.local.SmsCodeValidation
import com.rookia.android.sejo.domain.local.smscode.SmsCodeValidation
import com.rookia.android.sejo.framework.receivers.SMSBroadcastReceiver
import com.rookia.android.sejo.usecases.RequestSmsCodeUseCase
import com.rookia.android.sejo.usecases.ValidateSmsCodeUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class ValidateSmsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()


    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @MockK
    lateinit var requestSmsCodeUseCase: RequestSmsCodeUseCase

    @MockK
    lateinit var validateSmsCodeUseCase: ValidateSmsCodeUseCase

    @MockK
    lateinit var smsBroadcastReceiver: SMSBroadcastReceiver

    @MockK
    lateinit var preferencesManager: PreferencesManager


    val testDispatcher = TestCoroutineDispatcher()
    lateinit var viewModel: ValidateSmsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        if (Looper.myLooper() == null) {
            Looper.prepare()
        }
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = ValidateSmsViewModel(
            requestSmsCodeUseCase,
            validateSmsCodeUseCase,
            smsBroadcastReceiver,
            preferencesManager,
            testDispatcher
        )

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun requestSms() {
    }

    @Test
    fun `request sms code with success response from server`() {
        coEvery { requestSmsCodeUseCase.askForSmsCode(any(), any()) } returns flow {
            emit(Result.loading(null))
            emit(Result.success(1))
        }
        testDispatcher.runBlockingTest {
            val testLiveData = viewModel.requestSms("", "")

            val responseObserver = testLiveData.test()
            val latch = CountDownLatch(2)
            val observer = Observer<Result<Int>> {
                latch.countDown()
            }
            testLiveData.observeForever(observer)
            latch.await(20, TimeUnit.SECONDS)
            responseObserver
                .assertHasValue()
                .assertHistorySize(2)
                .assertValue(Result.success(1))
                .assertValueHistory(
                    Result.loading(null),
                    Result.success(1)
                )
        }
    }

    @Test
    fun `request sms code with error response from server`() {
        val errorMessage = "error"
        every { requestSmsCodeUseCase.askForSmsCode(any(), any()) } returns flow {
            emit(Result.loading(null))
            emit(Result.error(errorMessage, null))
        }
        testDispatcher.runBlockingTest {
            val testLiveData = viewModel.requestSms("", "")

            val responseObserver = testLiveData.test()
            val latch = CountDownLatch(2)
            val observer = Observer<Result<Int>> {
                latch.countDown()
            }
            testLiveData.observeForever(observer)
            latch.await(10, TimeUnit.SECONDS)
            responseObserver
                .assertHasValue()
                .assertHistorySize(2)
                .assertValue(Result.error(errorMessage, null))
                .assertValueHistory(
                    Result.loading(null),
                    Result.error(errorMessage, null)
                )
        }
    }

    @Test
    fun `validate sms code with success response from server`() {
        val smsCodeValidation = SmsCodeValidation(1, true)
        coEvery { validateSmsCodeUseCase.validateSmsCode(any(), any(), any()) } returns flow {
            emit(Result.loading(null))
            emit(Result.success(smsCodeValidation))
        }
        testDispatcher.runBlockingTest {
            viewModel.validateCode("", "", "")

            val responseObserver = viewModel.smsCodeValidationResult.test()
            val latch = CountDownLatch(2)
            val observer = Observer<Result<SmsCodeValidation>> {
                latch.countDown()
            }
            viewModel.smsCodeValidationResult.observeForever(observer)
            latch.await(10, TimeUnit.SECONDS)
            responseObserver
                .assertHasValue()
                .assertHistorySize(2)
                .assertValue(Result.success(smsCodeValidation))
                .assertValueHistory(
                    Result.loading(null),
                    Result.success(smsCodeValidation)
                )
        }
    }

    @Test
    fun `validate sms code with error response from server`() {
        val errorMessage = "error"
        every { validateSmsCodeUseCase.validateSmsCode(any(), any(), any()) } returns flow {
            emit(Result.loading(null))
            emit(Result.error(errorMessage, null))
        }
        testDispatcher.runBlockingTest {
            viewModel.validateCode("", "", "")

            val responseObserver = viewModel.smsCodeValidationResult.test()
            val latch = CountDownLatch(2)
            val observer = Observer<Result<SmsCodeValidation>> {
                latch.countDown()
            }
            viewModel.smsCodeValidationResult.observeForever(observer)
            latch.await(10, TimeUnit.SECONDS)
            responseObserver
                .assertHasValue()
                .assertHistorySize(2)
                .assertValue(Result.error(errorMessage, null))
                .assertValueHistory(
                    Result.loading(null),
                    Result.error(errorMessage, null)
                )
        }
    }

    @Test
    fun storeValidatedPhone() {

        val phoneNumber = "666666666"
        val phonePrefix = "+34"
        viewModel.storeValidatedPhone(phonePrefix, phoneNumber)

        verify {
            preferencesManager.setBooleanIntoPreferences(
                Constants.NAVIGATION_VALIDATED_PHONE_TAG,
                true
            )
        }
        verify {
            preferencesManager.setStringIntoPreferences(
                Constants.VALIDATED_PHONE_PREFIX_TAG,
                phonePrefix
            )
        }
        verify {
            preferencesManager.setStringIntoPreferences(
                Constants.VALIDATED_PHONE_NUMBER_TAG,
                phoneNumber
            )
        }

    }

    @Test
    fun getReceiver() {
        val receiver = viewModel.receiver
        assert(receiver == this.smsBroadcastReceiver)
    }
}