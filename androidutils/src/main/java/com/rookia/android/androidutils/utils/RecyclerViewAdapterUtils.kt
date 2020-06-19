package com.rookia.android.androidutils.utils

import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, June 2020
 *
 *
 */

class RecyclerViewAdapterUtils @Inject constructor() {

    fun scrollToTopIfItemsInsertedArePlacedBeforeTheFirstRow(adapter: RecyclerView.Adapter<*>, recyclerView: RecyclerView) {
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    recyclerView.layoutManager?.scrollToPosition(0)
                }
            }
        })
    }
}