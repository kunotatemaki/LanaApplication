package com.raul.androidapps.lanaapplication.ui.main

import androidx.lifecycle.*
import com.raul.androidapps.lanaapplication.domain.NetworkResponse
import com.raul.androidapps.lanaapplication.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class MainViewModel constructor(private val repository: Repository) : ViewModel() {

    private val foo: MutableLiveData<Any> = MutableLiveData()
    private val error: MutableLiveData<String> = MutableLiveData()
    private val loading: MutableLiveData<Boolean> = MutableLiveData()

    private lateinit var fooJob: Job

    fun getFooAsObservable(): LiveData<Any> = Transformations.distinctUntilChanged(foo)
    fun needToShowError(): LiveData<String> = error
    fun resetError() {
        error.value = null
    }

    fun needToShowLoading(): LiveData<Boolean> = Transformations.distinctUntilChanged(loading)

    fun getFoo(): Job =
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            when (val response = repository.getProducts()) {
                is NetworkResponse.Success -> {
                    loading.postValue(false)
                    foo.postValue(response.data)
                }
                is NetworkResponse.Failure -> {
                    loading.postValue(false)
                    error.postValue(response.message)
                }
            }
        }.also { fooJob = it }


    fun cancelFoo() {
        fooJob.cancel()
    }
}
