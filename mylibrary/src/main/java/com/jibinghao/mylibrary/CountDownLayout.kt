package com.jibinghao.mylibrary

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
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
class CountDownLayout<T> : FrameLayout, CountDownLifecycleObserver {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private var iCountDownCallBack: ICountDownCallBack? = null
    private var mShowFirst = true
    private var mFirst = 0//第一个view 数据下标
    private var mSecond = -1//第二个view 数据下标
    private var mItemLayoutId: Int = -1

    private var mItemCount = 2//默认一行2个 可以修改这个参数
    private var mData = listOf<List<Any>>()
    private var count = 0
    private var mViewList = mutableListOf<List<View>>()

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
                        updateData()
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

    private fun initItemView() {
        if (mItemCount <= 0) {
            throw Exception("最少要有一个Item")
        }
        if (mItemLayoutId == -1) {
            throw Exception("itemLayoutId为空")
        }

        val viewList1 = mutableListOf<View>()
        val viewList2 = mutableListOf<View>()
        for (i in 0 until mItemCount) {
            val itemView = getItemView()
            val itemView2 = getItemView()
            viewList1.add(itemView)
            viewList2.add(itemView2)
            ll_first.addView(itemView)
            ll_second.addView(itemView2)
        }
        mViewList.add(viewList1)
        mViewList.add(viewList2)
    }

    private fun initData() {
        count = mData.size
        setData(true, 0)
        if (mData.size > 1) {
            //超过1个才开始循环
            startAnimationCountDown()
            setData(false, 1)
        }
    }

    private fun setData(first: Boolean, index: Int) {
        var position = 0
        val size = mData[index].size
        while (position < mItemCount) {
            iCountDownCallBack?.updateItemData(
                mViewList[if (first) 0 else 1][position],
                if (position < size) mData[index][position] else null
            )
            position++
        }
    }

    fun getItemView(): View {
        return LayoutInflater.from(context).inflate(mItemLayoutId, null)
    }



    private fun updateData() {
        //判断集合是偶数还是奇数
        if (count % 2 == 0) {
            //偶数
            if (mShowFirst) {
                //显示BLayout
                mSecond = mFirst + 1
                setData(false, mSecond)
            } else {
                //显示ALayout
                if (mFirst + 2 == count) {
                    mFirst = 0
                } else {
                    mFirst += 2
                }
                setData(true, mFirst)
            }
        } else {
            //奇数
            if (mShowFirst) {
                //显示BLayout
                if (mSecond + 2 == count) {
                    //超过最大值了 改为0
                    mSecond = 0
                } else if (mSecond == count - 1) {
                    mSecond = 1
                } else {
                    mSecond += 2
                }
                setData(false, mSecond)
            } else {
                //显示ALayout
                if (mFirst + 2 == count) {
                    //超过最大值了 改为0
                    mFirst = 0
                } else if (mFirst == count - 1) {
                    mFirst = 1
                } else {
                    mFirst += 2
                }
                setData(true, mFirst)

            }
        }
    }

    fun init(
        layoutId: Int,
        itemCount: Int,
        data: List<List<Any>>,
        iCountDownCallBack: ICountDownCallBack
    ) {
        this.mItemLayoutId = layoutId
        this.mItemCount = itemCount
        this.mData = data
        this.iCountDownCallBack = iCountDownCallBack
        initItemView()
        initData()
    }
}