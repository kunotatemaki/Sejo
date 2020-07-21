package com.rookia.android.sejocoreandroid.domain.local

data class SmsCodeValidation(
    val result: Int,
    val userId: String?,
    val lastUsedGroup: Long?
)
