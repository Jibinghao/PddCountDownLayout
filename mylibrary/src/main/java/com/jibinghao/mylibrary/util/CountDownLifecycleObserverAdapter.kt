package com.jibinghao.mylibrary.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * FileName：CountDownLifecycleObserverAdapter
 * Description：
 * Copyright
 */
class CountDownLifecycleObserverAdapter(
    lifecycleOwner: LifecycleOwner,
    observer: CountDownLifecycleObserver
) : LifecycleObserver {
    private var mObserver: CountDownLifecycleObserver? = observer
    private var mLifecycleOwner: LifecycleOwner? = lifecycleOwner


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        LogUtils.i("onStart")
        mObserver?.onStart(mLifecycleOwner)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        LogUtils.i("onStop")
        mObserver?.onStop(mLifecycleOwner)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        LogUtils.i("onDestroy")
        mObserver?.onDestroy(mLifecycleOwner)
    }
}