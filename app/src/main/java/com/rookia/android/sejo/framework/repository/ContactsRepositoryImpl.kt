package com.rookia.android.sejo.framework.repository

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.framework.repository.resultOnlyFromOneSourceInFlow
import com.rookia.android.sejo.data.repository.ContactsRepository
import com.rookia.android.sejo.domain.local.PhoneContact
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

class ContactsRepositoryImpl @Inject constructor(private val context: Context) :
    ContactsRepository {

    override fun loadContacts(): Flow<Result<List<PhoneContact>>> =
        resultOnlyFromOneSourceInFlow {
            loadContactsInternal()
        }

    private fun loadContactsInternal(): Result<List<PhoneContact>> {

        val phoneList: MutableList<PhoneContact> = mutableListOf()

        val phones: Cursor? = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null
        )
        phones?.let {

            while (phones.moveToNext()) {
                val hasNumber = phones.getShort(
                    phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER
                    )
                )
                if (hasNumber == 0.toShort()) continue
                val phoneNumberNormalized = phones.getString(
                    phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER
                    )
                ) ?: continue
                val phoneNumber = phones.getString(
                    phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                ) ?: continue
                val contactName = phones.getString(
                    phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY
                    )
                ) ?: continue
                val thumbnail = phones.getString(
                    phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
                    )
                )
                val id = phones.getString(
                    phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                    )
                ) ?: continue
                phoneList.add(
                    PhoneContact(
                        phoneNumber = phoneNumber,
                        phoneNumberNormalized = phoneNumberNormalized,
                        name = contactName,
                        photoUrl = thumbnail,
                        id = id
                    )
                )
            }
        }
        phones?.close()

        return Result.success(phoneList)
    }
}