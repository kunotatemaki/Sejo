package com.rookia.android.sejocoreandroid.domain.local


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

enum class MemberStates(val code: Int) {
    OWNER(0), VALIDATED(1), PENDING(2), GUESS(3)
}
