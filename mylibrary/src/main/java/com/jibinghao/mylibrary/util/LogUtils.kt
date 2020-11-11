package com.jibinghao.mylibrary.util

import android.util.Log

/**
 * FileName：LogUtils
 * Description：
 * Copyright
 */
object LogUtils {
    private const val TAG = "COUNT_DOWN"

    private const val DEBUG: Boolean = true

    fun d(msg: String?) {
        if (DEBUG) {
            Log.d(TAG, msg!!)
        }
    }

    fun e(msg: String?) {
        if (DEBUG) {
            Log.e(TAG, msg!!)
        }
    }

    fun i(msg: String?) {
        if (DEBUG) {
            Log.i(TAG, msg!!)
        }
    }

    fun v(msg: String?) {
        if (DEBUG) {
            Log.v(TAG, msg!!)
        }
    }

    fun w(msg: String?) {
        if (DEBUG) {
            Log.w(TAG, msg!!)
        }
    }
}