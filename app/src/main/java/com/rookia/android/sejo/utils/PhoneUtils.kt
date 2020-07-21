package com.rookia.android.sejo.utils

import com.rookia.android.androidutils.preferences.PreferencesManager
import com.rookia.android.sejo.Constants.SpanishPhone.NUMBER_MAX_LENGTH_WITHOUT_SPACES
import com.rookia.android.sejo.Constants.SpanishPhone.NUMBER_PATTERN_MATCHER_EIGHT_DIGITS
import com.rookia.android.sejo.Constants.SpanishPhone.NUMBER_PATTERN_MATCHER_FIVE_DIGITS
import com.rookia.android.sejo.Constants.SpanishPhone.NUMBER_PATTERN_MATCHER_FOUR_DIGITS
import com.rookia.android.sejo.Constants.SpanishPhone.NUMBER_PATTERN_MATCHER_NINE_DIGITS
import com.rookia.android.sejo.Constants.SpanishPhone.NUMBER_PATTERN_MATCHER_SEVEN_DIGITS
import com.rookia.android.sejo.Constants.SpanishPhone.NUMBER_PATTERN_MATCHER_SIX_DIGITS
import com.rookia.android.sejo.Constants.SpanishPhone.NUMBER_PATTERN_REPLACEMENT_FOUR_GROUP
import com.rookia.android.sejo.Constants.SpanishPhone.NUMBER_PATTERN_REPLACEMENT_THREE_GROUP
import com.rookia.android.sejo.Constants.SpanishPhone.NUMBER_PATTERN_REPLACEMENT_TWO_GROUP
import com.rookia.android.sejo.Constants.SpanishPhone.NUMBER_PREFIX
import com.rookia.android.sejo.Constants.SpanishPhone.NUMBER_START_PATTERN_MATCHER
import com.rookia.android.sejo.Constants.UserData.PHONE_NUMBER_TAG
import com.rookia.android.sejo.Constants.UserData.PHONE_PREFIX_TAG
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

class PhoneUtils @Inject constructor() {

    fun isValidPhoneNumber(prefix: String, phone: String): Boolean {

        //Remove spaces
        val cleanPhone = phone.replace("[\\s]".toRegex(), "")
        val maxLengthWithoutSpaces = when (prefix) {
            NUMBER_PREFIX -> NUMBER_MAX_LENGTH_WITHOUT_SPACES
            else -> 0
        }
        return cleanPhone.length == maxLengthWithoutSpaces &&
                cleanPhone.toIntOrNull() != null &&
                phoneStartsWithValidNumber(prefix, cleanPhone)
    }

    fun isPhoneNumberYourActualPhone(
        prefix: String,
        phone: String,
        preferencesManager: PreferencesManager
    ): Boolean {
        val storedPhoneNumber: String? =
            preferencesManager.getStringFromPreferences(PHONE_NUMBER_TAG)
        val storedPhonePrefix: String? =
            preferencesManager.getStringFromPreferences(PHONE_PREFIX_TAG)
        return storedPhoneNumber == phone && storedPhonePrefix == prefix
    }

    fun phoneStartsWithValidNumber(prefix: String, phone: String): Boolean {

        //Remove spaces
        val cleanPhone = phone.replace("[\\s]".toRegex(), "")
        val patternString = when (prefix) {
            NUMBER_PREFIX -> NUMBER_START_PATTERN_MATCHER
            else -> ""
        }
        val pattern = Pattern.compile(patternString)
        val matcher = pattern.matcher(cleanPhone)
        return matcher.find() || cleanPhone.isEmpty()

    }

    /**
     * **Progressively** format a string as a valid phone number.
     * To format a full number please use [PhoneUtils.formatWithSpaces]
     * @param prefix International calling prefix
     * @param phone WITHOUT PREFIX
     * @param includePrefix return with or without prefix
     * @return
     */
    fun formatWithSpaces(prefix: String, phone: String, includePrefix: Boolean): String {
        val phoneNumberWithoutSpaces = phone.replace("[^\\d\\s]".toRegex(), "")
        val phoneNumberFormatted = when (prefix) {
            NUMBER_PREFIX -> formatWithSpacesSpanish(phoneNumberWithoutSpaces)
            else -> phoneNumberWithoutSpaces
        }
        return if (includePrefix) {
            "$prefix $phoneNumberFormatted"
        } else {
            phoneNumberFormatted
        }
    }

    /**
     * Format phone number to ### ### ###format
     * @param phone WITHOUT PREFIX
     * @return
     */
    private fun formatWithSpacesSpanish(
        phone: String
    ): String {
        //Remove spaces
        val cleanPhone = phone.replace("[\\s]".toRegex(), "")
        return when (cleanPhone.length) {
            9 -> cleanPhone.replace(
                NUMBER_PATTERN_MATCHER_NINE_DIGITS.toRegex(),
                NUMBER_PATTERN_REPLACEMENT_FOUR_GROUP
            )
            8 -> cleanPhone.replace(
                NUMBER_PATTERN_MATCHER_EIGHT_DIGITS.toRegex(),
                NUMBER_PATTERN_REPLACEMENT_FOUR_GROUP
            )
            7 -> cleanPhone.replace(
                NUMBER_PATTERN_MATCHER_SEVEN_DIGITS.toRegex(),
                NUMBER_PATTERN_REPLACEMENT_THREE_GROUP
            )
            6 -> cleanPhone.replace(
                NUMBER_PATTERN_MATCHER_SIX_DIGITS.toRegex(),
                NUMBER_PATTERN_REPLACEMENT_THREE_GROUP
            )
            5 -> cleanPhone.replace(
                NUMBER_PATTERN_MATCHER_FIVE_DIGITS.toRegex(),
                NUMBER_PATTERN_REPLACEMENT_TWO_GROUP
            )
            4 -> cleanPhone.replace(
                NUMBER_PATTERN_MATCHER_FOUR_DIGITS.toRegex(),
                NUMBER_PATTERN_REPLACEMENT_TWO_GROUP
            )
            else -> cleanPhone

        }
    }
}