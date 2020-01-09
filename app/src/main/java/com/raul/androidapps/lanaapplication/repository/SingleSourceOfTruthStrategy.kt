package com.raul.androidapps.lanaapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.raul.androidapps.lanaapplication.vo.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay


fun <T, A> resultLiveData(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Result<A>,
    saveCallResult: suspend (A?) -> Unit,
    runNetworkCall: Boolean
): LiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        val disposable = emitSource(
            databaseQuery.invoke().map {
                if (runNetworkCall) {
                    //show data from db but keep the loading state, as a network call will be done
                    Result.loading(it)
                } else {
                    //no network call -> show success
                    Result.success(it)
                }
            }
        )
        if (runNetworkCall) {
            val responseStatus = networkCall.invoke()
            // Stop the previous emission to avoid dispatching the updated user
            // as `loading`.
            disposable.dispose()
            emitSource(
                if (responseStatus.status == Result.Status.ERROR) {
                    databaseQuery.invoke().map {
                        Result.error(responseStatus.message, it)
                    }
                } else {
                    saveCallResult.invoke(responseStatus.data)
                    databaseQuery.invoke().map {
                        Result.success(it)
                    }
                }
            )
        }

    }
 
 