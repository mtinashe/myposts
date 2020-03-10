package com.mtinashe.myposts.test_utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import org.mockito.Mockito

inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

@Throws(InterruptedException::class)
fun <T> LiveData<T>.getValueBlocking(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)
    val innerObserver = Observer<T> {
        value = it
        latch.countDown()
    }
    observeForever(innerObserver)
    latch.await(2, TimeUnit.SECONDS)
    return value
}
