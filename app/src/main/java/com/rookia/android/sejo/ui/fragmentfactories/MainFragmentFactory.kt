package com.rookia.android.sejo.ui.fragmentfactories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.rookia.android.sejo.data.repository.GroupRepository
import com.rookia.android.sejo.ui.BlankFragment
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

class MainFragmentFactory @Inject constructor(
    private val groupRepository: GroupRepository
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            BlankFragment::class.java -> BlankFragment(groupRepository)
            else -> super.instantiate(classLoader, className)
        }
    }
}