package com.rookia.android.sejocore.domain.local

data class SmsCodeValidation(
    val result: Int,
    val userId: String?,
    val lastUsedGroup: Long?
)
