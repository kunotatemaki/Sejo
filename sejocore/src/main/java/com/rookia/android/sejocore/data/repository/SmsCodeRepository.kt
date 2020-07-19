package com.rookia.android.sejocore.data.repository

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.kotlinutils.repository.resultOnlyFromOneSourceInFlow
import com.rookia.android.sejocore.data.remote.SmsCodeRemoteDataSource
import com.rookia.android.sejocore.domain.local.SmsCodeValidation
import kotlinx.coroutines.flow.Flow

class SmsCodeRepository constructor(
    private val remoteDataSource: SmsCodeRemoteDataSource
)  {
    fun askForSmsCode(phonePrefix: String, phoneNumber: String): Flow<Result<Int>> =
        resultOnlyFromOneSourceInFlow {
            remoteDataSource.requestSmsFromServer(phonePrefix, phoneNumber)
        }

    fun validateSmsCode(
        phonePrefix: String,
        phoneNumber: String,
        smsCode: String
    ): Flow<Result<SmsCodeValidation>> =
        resultOnlyFromOneSourceInFlow {
            remoteDataSource.validateSmsFromServer(phonePrefix, phoneNumber, smsCode)
        }

}