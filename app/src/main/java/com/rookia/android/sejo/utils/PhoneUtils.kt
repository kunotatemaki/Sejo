package com.rookia.android.sejo.utils

import androidx.core.text.isDigitsOnly
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.Constants.SIGN_IN_PROCESS_VALIDATED_PHONE_NUMBER_TAG
import com.rookia.android.sejo.Constants.SIGN_IN_PROCESS_VALIDATED_PHONE_PREFIX_TAG
import com.rookia.android.sejo.Constants.SPANISH_PHONE_NUMBER_MAX_LENGTH_WITHOUT_SPACES
import com.rookia.android.sejo.Constants.SPANISH_PHONE_NUMBER_MAX_LENGTH_WITH_SPACES
import com.rookia.android.sejo.Constants.SPANISH_PHONE_NUMBER_PATTERN_MATCHER
import com.rookia.android.sejo.Constants.SPANISH_PHONE_NUMBER_PATTERN_REPLACEMENT
import com.rookia.android.sejo.Constants.SPANISH_PHONE_NUMBER_PREFIX
import com.rookia.android.sejo.Constants.SPANISH_PHONE_NUMBER_START_PATTERN_MATCHER
import java.util.regex.Pattern
import javax.inject.Inject


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, April 2020
 *
 *
 */

class PhoneUtils @Inject constructor(private val preferencesManager: PreferencesManager) {

    fun isValidPhoneNumber(prefix: String, phone: String): Boolean {

        //Remove spaces
        val cleanPhone = phone.replace("[\\s]".toRegex(), "")
        val maxLengthWithoutSpaces = when (prefix) {
            SPANISH_PHONE_NUMBER_PREFIX -> SPANISH_PHONE_NUMBER_MAX_LENGTH_WITHOUT_SPACES
            else -> 0
        }
        return cleanPhone.length == maxLengthWithoutSpaces &&
                cleanPhone.toIntOrNull() != null &&
                phoneStartsWithValidNumber(prefix, cleanPhone)
    }

    fun isPhoneNumberYourActualPhone(prefix: String, phone: String): Boolean {
        val storedPhoneNumber: String? = preferencesManager.getStringFromPreferences(SIGN_IN_PROCESS_VALIDATED_PHONE_NUMBER_TAG)
        val storedPhonePrefix: String? = preferencesManager.getStringFromPreferences(SIGN_IN_PROCESS_VALIDATED_PHONE_PREFIX_TAG)
        return storedPhoneNumber == phone && storedPhonePrefix == prefix
    }

    fun phoneStartsWithValidNumber(prefix: String, phone: String): Boolean {

        //Remove spaces
        val cleanPhone = phone.replace("[\\s]".toRegex(), "")
        val patternString = when (prefix) {
            SPANISH_PHONE_NUMBER_PREFIX -> SPANISH_PHONE_NUMBER_START_PATTERN_MATCHER
            else -> ""
        }
        val pattern = Pattern.compile(patternString)
        val matcher = pattern.matcher(cleanPhone)
        return matcher.find() || cleanPhone.isEmpty()

    }

    /**
     * **Progressively** format a string as a valid phone number.
     * To format a full number please use [PhoneUtils.formatWithSpaces]
     * @param phone
     * @return
     */
    fun progressiveFormat(prefix: String, phone: String): String {
        var ret = ""
        when (prefix) {
            SPANISH_PHONE_NUMBER_PREFIX -> ret = progressiveFormatSpanishPhone(phone)
        }
        return ret
    }

    private fun progressiveFormatSpanishPhone(phone: String): String {
        var ret = ""

        //Remove non numeric, keep spaces
        ret = phone.replace("[^\\d\\s]".toRegex(), "")
        if (ret.length == 4) {
            ret = ret.substring(0, 3) + " " + ret.substring(3)
        }
        if (ret.length == 8) {
            ret = ret.substring(0, 7) + " " + ret.substring(7)
        }
        ret = ret.trim()
        return if(ret.length > SPANISH_PHONE_NUMBER_MAX_LENGTH_WITH_SPACES) {
            ret.substring(0, SPANISH_PHONE_NUMBER_MAX_LENGTH_WITH_SPACES)
        } else {
            ret
        }
    }

    /**
     * Format phone number to ### ### ###format
     * @param prefix International calling prefix
     * @param phone WITHOUT PREFIX
     * @param includePrefix return with or without prefix
     * @return
     */
    fun formatWithSpaces(
        prefix: String,
        phone: String,
        includePrefix: Boolean
    ): String {
        var ret = ""
        //Remove spaces
        val cleanPhone = phone.replace("[\\s]".toRegex(), "")
        var maxLength = 0
        var matcher = ""
        var replacement = ""
        when (prefix) {
            SPANISH_PHONE_NUMBER_PREFIX -> {
                maxLength = SPANISH_PHONE_NUMBER_MAX_LENGTH_WITHOUT_SPACES
                matcher = SPANISH_PHONE_NUMBER_PATTERN_MATCHER
                replacement = SPANISH_PHONE_NUMBER_PATTERN_REPLACEMENT
            }
        }
        if (phone.length == maxLength) {
            ret = cleanPhone.replace(matcher.toRegex(), replacement)
        }
        if (includePrefix) {
            ret = "$prefix $ret"
        }
        return ret
    }
}