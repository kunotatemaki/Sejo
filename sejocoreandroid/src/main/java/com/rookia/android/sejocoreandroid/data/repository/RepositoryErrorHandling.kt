package com.rookia.android.sejocoreandroid.data.repository

import com.google.gson.Gson
import com.rookia.android.sejocoreandroid.domain.remote.ErrorBody
import retrofit2.Response

interface RepositoryErrorHandling {

    fun <T> getErrorMessage(response: Response<T>): String {
        val errorBody = Gson().fromJson(response.errorBody()?.string(), ErrorBody::class.java)
        return "${response.message()} - ${errorBody.detailMessage}"
    }

}
