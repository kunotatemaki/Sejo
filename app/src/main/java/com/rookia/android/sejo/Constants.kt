package com.rookia.android.sejo


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

object Constants {

    //region PHONE NUMBER FORMAT
    const val SPANISH_PHONE_NUMBER_PREFIX = "+34"
    const val SPANISH_PHONE_NUMBER_MAX_LENGTH_WITH_SPACES = 11
    const val SPANISH_PHONE_NUMBER_MAX_LENGTH_WITHOUT_SPACES = 9

    const val SPANISH_PHONE_NUMBER_PATTERN_MATCHER_NINE_DIGITS = "(\\d{3})(\\d{2})(\\d{2})(\\d{2})"
    const val SPANISH_PHONE_NUMBER_PATTERN_MATCHER_EIGHT_DIGITS = "(\\d{3})(\\d{2})(\\d{2})(\\d{1})"
    const val SPANISH_PHONE_NUMBER_PATTERN_MATCHER_SEVEN_DIGITS = "(\\d{3})(\\d{2})(\\d{2})"
    const val SPANISH_PHONE_NUMBER_PATTERN_MATCHER_SIX_DIGITS = "(\\d{3})(\\d{2})(\\d{1})"
    const val SPANISH_PHONE_NUMBER_PATTERN_MATCHER_FIVE_DIGITS = "(\\d{3})(\\d{2})"
    const val SPANISH_PHONE_NUMBER_PATTERN_MATCHER_FOUR_DIGITS = "(\\d{3})(\\d{1})"
    const val SPANISH_PHONE_NUMBER_PATTERN_REPLACEMENT_ONE_GROUP = "$1"
    const val SPANISH_PHONE_NUMBER_PATTERN_REPLACEMENT_TWO_GROUP = "$1 $2"
    const val SPANISH_PHONE_NUMBER_PATTERN_REPLACEMENT_THREE_GROUP = "$1 $2 $3"
    const val SPANISH_PHONE_NUMBER_PATTERN_REPLACEMENT_FOUR_GROUP = "$1 $2 $3 $4"
    const val SPANISH_PHONE_NUMBER_START_PATTERN_MATCHER = "^[678].*"

    //endregion

    //region SMS CODE
    const val SMS_PIN_LENGTH = 6
    const val SMS_RESEND_WAITING_TIME = 30
    const val HAS_VALIDATED_PHONE_TAG = "PREF_TAGS_HAS_VALIDATED_PHONE_TAG"
    const val VALIDATED_PHONE_NUMBER_TAG = "PREF_TAGS_VALIDATED_PHONE_NUMBER_TAG"
    const val VALIDATED_PHONE_PREFIX_TAG = "PREF_TAGS_VALIDATED_PHONE_PREFIX_TAG"

    //endregion

    //region PASSWORD
    const val PASSWORD_LENGTH: Int = 4
    val ERROR_VIBRATION_PATTERN: LongArray =
        longArrayOf(100, 20, 100, 20, 100, 20, 100, 20, 100, 20)
    //endregion

    //region SERVER CONNECTION

    //endregion

    //region SERVER RESPONSE CODES
    const val GENERAL_RESPONSE_OK = -1
    const val GENERAL_RESPONSE_ERROR = 1
    const val SMS_CODE_OK = 0
    const val SMS_CODE_ERROR = 1
    const val SMS_CODE_EXPIRED = 2
    //endregion

    //region NAVIGATION
    const val NAVIGATION_VALIDATED_PHONE_TAG = "NAVIGATION_VALIDATED_PHONE_NUMBER_TAG"
    const val NAVIGATION_PASSWORD_TAG = "NAVIGATION_PASSWORD_TAG"

    //endregion

    //region USER DATA
    const val USER_TOKEN_TAG = "USER_TOKEN_TAG"
    const val USER_PASSWORD_TAG = "USER_PASSWORD_TAG"
    const val USER_PASSWORD_ALIAS = "com.rookia.android.sejo"
    const val USER_BIOMETRICS_TAG = "USER_BIOMETRICS_TAG"
    const val USER_PHONE_NUMBER_TAG = "USER_BIOMETRICS_TAG"
    const val USER_PHONE_PREFIX_TAG = "USER_BIOMETRICS_TAG"


    //endregion
}