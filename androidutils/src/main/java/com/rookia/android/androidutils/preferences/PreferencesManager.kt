package com.rookia.android.androidutils.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import com.rookia.android.androidutils.utils.Encryption
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    @ApplicationContext private var context: Context,
    private val encryption: Encryption?
) {

    fun getIntFromPreferences(key: String, default: Int = -1): Int {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getInt(key, default)
    }

    fun getStringFromPreferences(key: String): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(key, null)
    }

    fun getEncryptedStringFromPreferences(key: String, alias: String): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val encrypted = prefs.getString(key, "")
        return encryption?.decryptString(encrypted, alias)
    }

    fun getBooleanFromPreferences(key: String, default: Boolean = false): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean(key, default)
    }

    fun getLongFromPreferences(key: String, default: Long = -1L): Long {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getLong(key, default)
    }

    fun getFloatFromPreferences(key: String, default: Float = -1f): Float {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getFloat(key, default)
    }

    fun setIntIntoPreferences(key: String, value: Int) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putInt(key, value).apply()
    }

    fun setStringIntoPreferences(key: String, value: String?) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString(key, value).apply()
    }

    fun setEncryptedStringIntoPreferences(
        key: String,
        value: String,
        alias: String
    ): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val encryptedText = encryption?.encryptString(value, alias)
        return if (encryptedText.isNullOrBlank().not()) {
            prefs.edit().putString(key, encryptedText).apply()
            true
        } else {
            prefs.edit().putString(key, value).apply()
            false
        }
    }

    fun setBooleanIntoPreferences(key: String, value: Boolean) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putBoolean(key, value).apply()
    }

    fun setLongIntoPreferences(key: String, value: Long) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putLong(key, value).apply()
    }

    fun setFloatIntoPreferences(key: String, value: Float) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putFloat(key, value).apply()
    }

    fun deleteVarFromSharedPreferences(key: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().remove(key).apply()
    }

    fun containsKey(key: String): Boolean =
        PreferenceManager.getDefaultSharedPreferences(context).contains(key)

    fun clearAll() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().clear().apply()
    }
}