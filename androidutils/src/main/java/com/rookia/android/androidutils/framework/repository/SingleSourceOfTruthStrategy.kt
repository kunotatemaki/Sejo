package com.rookia.android.androidutils.framework.repository

import com.rookia.android.androidutils.domain.vo.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


fun <T, A> resultFromPersistenceAndNetworkInFlow(
    persistedDataQuery: () -> T,
    networkCall: suspend () -> Result<A>,
    persistCallResult: suspend (A?) -> Unit,
    isThePersistedInfoOutdated: (T?) -> Boolean
): Flow<Result<T>> =
    flow {

        var cachedData = persistedDataQuery.invoke()
        val needToGetInfoFromServer = isThePersistedInfoOutdated(cachedData)
        if (needToGetInfoFromServer) {
            //show data from db but keep the loading state, as a network call will be done
            emit(Result.loading(cachedData))
        } else {
            //no network call -> show success
            emit(Result.success(cachedData))
        }

        if (needToGetInfoFromServer) {
            val responseStatus = networkCall.invoke()
            // Stop the previous emission to avoid dispatching the updated user
            // as `loading`.
            if (responseStatus.status == Result.Status.ERROR) {
                emit(Result.error(responseStatus.message, cachedData))
            } else {
                persistCallResult.invoke(responseStatus.data)
                cachedData = persistedDataQuery.invoke()
                emit(Result.success(cachedData))
            }
        }
    }


fun <T> resultOnlyFromOneSourceInFlow(
    sourceCall: suspend () -> Result<T>
): Flow<Result<T>> =
    flow {
        emit(
            Result.loading(null)
        )
        emit(
            sourceCall.invoke()
        )
    }

suspend fun <T, A> resultFromPersistenceAndNetwork(
    persistedDataQuery: suspend () -> T?,
    networkCall: suspend () -> Result<A>,
    persistCallResult: suspend (A?) -> Unit,
    isThePersistedInfoOutdated: (T?) -> Boolean
): T? {
    val persistedData = persistedDataQuery.invoke()
    return if (isThePersistedInfoOutdated(persistedData)) {
        //network call and persist the result
        val responseFromNetwork = networkCall.invoke()
        if (responseFromNetwork.status == Result.Status.SUCCESS) {
            persistCallResult.invoke(responseFromNetwork.data)
        }
        persistedDataQuery.invoke()
    } else {
        persistedData
    }
}

