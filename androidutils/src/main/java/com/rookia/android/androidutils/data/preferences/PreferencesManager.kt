package com.rookia.android.androidutils.data.preferences

interface PreferencesManager {

    fun getIntFromPreferences(key: String, default: Int = -1): Int
    fun getStringFromPreferences(key: String): String?
    fun getEncryptedStringFromPreferences(key: String, alias: String): String?
    fun getBooleanFromPreferences(key: String, default: Boolean = false): Boolean
    fun getLongFromPreferences(key: String, default: Long = -1L): Long
    fun getFloatFromPreferences(key: String, default: Float = -1f): Float
    fun setIntIntoPreferences(key: String, value: Int)
    fun setStringIntoPreferences(key: String, value: String?)
    fun setEncryptedStringIntoPreferences(key: String, value: String, alias: String): Boolean
    fun setBooleanIntoPreferences(key: String, value: Boolean)
    fun setLongIntoPreferences(key: String, value: Long)
    fun setFloatIntoPreferences(key: String, value: Float)
    fun deleteVarFromSharedPreferences(key: String)
    fun containsKey(key: String): Boolean
    fun clearAll()

}