package com.rookia.android.sejoandroidframework.domain.remote

data class SmsCodeValidationServer(
    val code: Int,
    val message: String,
    val data: SmsCodeValidationResponse
){
    data class SmsCodeValidationResponse(
        val userId: String?,
        val lastUsedGroup: Long?
    )
}
