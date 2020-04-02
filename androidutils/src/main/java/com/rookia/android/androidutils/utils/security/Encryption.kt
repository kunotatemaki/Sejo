package com.rookia.android.androidutils.utils.security


interface Encryption {

    fun encryptString(text: String?, alias: String): String
    fun decryptString(text: String?, alias: String): String
}