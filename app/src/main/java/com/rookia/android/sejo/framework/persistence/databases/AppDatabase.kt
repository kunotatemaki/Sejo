package com.rookia.android.sejo.framework.persistence.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rookia.android.androidutils.framework.persistence.utils.DbConverters
import com.rookia.android.sejo.framework.persistence.daos.GroupDao
import com.rookia.android.sejo.framework.persistence.daos.MembersDao
import com.rookia.android.sejo.framework.persistence.entities.GroupEntity
import com.rookia.android.sejo.framework.persistence.entities.MemberEntity
import com.rookia.android.sejo.framework.persistence.utils.PersistenceConstants


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, May 2020
 *
 *
 */

@Database(entities = [(GroupEntity::class), (MemberEntity::class)], exportSchema = false, version = 1)
@TypeConverters(DbConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun membersDao(): MembersDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: buildDatabase(
                context
            ).also { INSTANCE = it }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, PersistenceConstants.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()

    }
}