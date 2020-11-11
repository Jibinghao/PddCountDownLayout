package com.jibinghao.mylibrary.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View

/**
 * FileName：BaseCountDownView
 * Description：
 * Copyright
 */
abstract class BaseItemLayout(context: Context) {
    private var context: Context = context

    private var mContentView: View? = null

    open fun onCreateContentView(layoutId: Int) {
        mContentView = LayoutInflater.from(context).inflate(layoutId, null)
    }

    abstract fun updateData()

}