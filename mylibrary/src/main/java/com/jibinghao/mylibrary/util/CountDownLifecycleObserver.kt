package com.jibinghao.mylibrary.util

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * FileName：CountDownLifecycleObserver
 * Description：
 * Copyright
 */
interface CountDownLifecycleObserver : LifecycleObserver {

    fun onStop(owner: LifecycleOwner?)

    fun onStart(owner: LifecycleOwner?)

    fun onDestroy(owner: LifecycleOwner?)
}