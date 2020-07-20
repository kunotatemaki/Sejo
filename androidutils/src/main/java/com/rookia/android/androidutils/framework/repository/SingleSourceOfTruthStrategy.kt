package com.rookia.android.androidutils.framework.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.paging.PagedList
import com.rookia.android.kotlinutils.domain.vo.Result

fun <T, A> resultFromPersistenceAndNetworkInLivePagedList(
    persistedDataQuery: () -> LiveData<PagedList<T>>,
    networkCall: suspend () -> Result<A>,
    persistCallResult: suspend (A?) -> Unit,
    isThePersistedInfoOutdated: () -> Boolean
): LiveData<Result<PagedList<T>>> =
    liveData {

        var cachedData = persistedDataQuery.invoke()
        val needToGetInfoFromServer = isThePersistedInfoOutdated.invoke()
        if (needToGetInfoFromServer) {
            //show data from db but keep the loading state, as a network call will be done
            emitSource(cachedData.map {  Result.loading(cachedData.value)})

        } else {
            //no network call -> show success
            emitSource(cachedData.map {  Result.success(cachedData.value)})
        }

        if (needToGetInfoFromServer) {
            val responseStatus = networkCall.invoke()
            // Stop the previous emission to avoid dispatching the updated user
            // as `loading`.
            persistCallResult.invoke(responseStatus.data)
            cachedData = persistedDataQuery.invoke()
            if (responseStatus.status == Result.Status.ERROR) {
                emitSource(cachedData.map {  Result.error(responseStatus.message, cachedData.value)})
            } else {
                emitSource(cachedData.map {  Result.success(cachedData.value)})
            }
        }
    }


