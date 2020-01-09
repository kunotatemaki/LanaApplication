package com.raul.androidapps.testapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit





@Throws(InterruptedException::class)
fun <T> LiveData<T>.getAllItem(): List<T> {
    val data = mutableListOf<T>()
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            data.add(t)
            latch.countDown()
            removeObserver(this)
        }
    }
    observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)
    return data
}