package com.rookia.android.kotlinutils.utils

import java.util.concurrent.TimeUnit


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

class RateLimiter {

    /**
     * @param timeout: max time to wait until the info is fetched again
     * @param timeUnit: the format in which #timeout is provided
     * @return true if the info need to be fetched again, false otherwise
     */
    @Synchronized
    fun expired(timeToCheck: Long, timeout: Int, timeUnit: TimeUnit): Boolean {
        val timeoutFormatted: Long = timeUnit.toMillis(timeout.toLong())
        val now = now()
        if (timeToCheck == 0L) {
            return true
        }
        return now - timeToCheck > timeoutFormatted
    }

    private fun now(): Long {
        return System.currentTimeMillis()
    }

}