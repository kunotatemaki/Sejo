package com.rookia.android.sejo.framework.utils

import java.time.Instant
import java.time.format.DateTimeFormatter
import javax.inject.Inject


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, July 2020
 *
 *
 */

class DateUtils @Inject constructor() {
    fun convertZuluTimeToUTCTimestamp(zulu: String?): Long =
        Instant.parse(zulu).epochSecond

    fun convertUTCTimestampToZuluTime(timestamp: Long?): String =
        DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochMilli(timestamp ?: 0L))

}