package com.rookia.android.sejocore.data.repository

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.kotlinutils.repository.resultOnlyFromOneSourceInFlow
import com.rookia.android.sejocore.data.local.ContactsLocalDataSource
import com.rookia.android.sejocore.domain.local.PhoneContact
import kotlinx.coroutines.flow.Flow

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

class ContactsRepository constructor(
    private val contactsLocalDataSource: ContactsLocalDataSource
) {

    fun loadContacts(): Flow<Result<List<PhoneContact>>> =
        resultOnlyFromOneSourceInFlow {
            contactsLocalDataSource.loadContacts()
        }

}