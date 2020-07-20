package com.rookia.android.sejo.usecases

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocore.data.repository.ContactsRepository
import com.rookia.android.sejocore.domain.local.PhoneContact
import kotlinx.coroutines.flow.Flow
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

class GetContactsUseCase @Inject constructor(private val repository: ContactsRepository) {

    fun loadContacts(): Flow<Result<List<PhoneContact>>> = repository.loadContacts()

}