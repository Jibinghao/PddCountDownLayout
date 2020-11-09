package com.jibinghao.mylibrary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import com.jibinghao.mylibrary.interf.ICountDownCallBack
import com.jibinghao.mylibrary.util.AnimationUtils.hide
import com.jibinghao.mylibrary.util.AnimationUtils.show
import com.jibinghao.mylibrary.util.CountDownLifecycleObserver
import com.jibinghao.mylibrary.util.CountDownLifecycleObserverAdapter
import com.jibinghao.mylibrary.util.LogUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.count_down_layout.view.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

/**
 * FileName：CountDownLayout
 * Description：
 * Copyright
 */
class CountDownLayout : FrameLayout, CountDownLifecycleObserver {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private val iCountDownCallBack: ICountDownCallBack? = null
    private var mShowFirst = true
    private var viewHolder: CountDownViewHolder? = null
    private var mItemCount = 2//默认一行2个 可以修改这个参数

    companion object {
        var animationDuration = 1000L//动画时间
        var countDownDuration = 4000L//每隔多久滑动一次
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
        startAnimationCountDown()
    }

    private fun initView() {
        LayoutInflater.from(context)
            .inflate(R.layout.count_down_layout, this)
    }

    fun addLifecycleObserver(owner: LifecycleOwner?) {
        owner?.lifecycle?.addObserver(CountDownLifecycleObserverAdapter(owner, this))
    }


    //启动轮播动画
    private fun startAnimationCountDown() {
        mCompositeDisposable.add(
            Observable.interval(0, countDownDuration, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Long>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: Long) {
                        //第一次样式什么都不用变
                        if (t == 0L) {
                            return
                        }
                        iCountDownCallBack?.updateItemData()
                        show(if (mShowFirst) ll_second else ll_first)
                        hide(if (mShowFirst) ll_first else ll_second)
                        mShowFirst = !mShowFirst
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    override fun onStop(owner: LifecycleOwner?) {
        LogUtils.e("onStop")

    }

    override fun onStart(owner: LifecycleOwner?) {
        LogUtils.e("onStart")

    }

    override fun onDestroy(owner: LifecycleOwner?) {
        LogUtils.e("onDestroy")
    }

    fun initItemView() {
        if (mItemCount <= 0) {
            throw Exception("最少要有一个Item")
        }

        for (i in 0 until mItemCount) {
            val itemView = viewHolder!!.itemView
            ll_first.addView(itemView)
        }
    }

}