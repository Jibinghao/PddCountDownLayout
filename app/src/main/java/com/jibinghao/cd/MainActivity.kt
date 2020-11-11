package com.jibinghao.cd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.jibinghao.mylibrary.interf.ICountDownCallBack
import com.jibinghao.mylibrary.util.LogUtils
import com.jibinghao.mylibrary.util.Tools
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        count_down.addLifecycleObserver(this)
        val list = mutableListOf<OrderEntity>()
        list.add(OrderEntity("放逐之刃", System.currentTimeMillis() + 1000 * 60, 2))
        list.add(OrderEntity("盲僧", System.currentTimeMillis() + 1000 * 160, 1))
        list.add(OrderEntity("暗夜猎手", System.currentTimeMillis() + 1000 * 460, 5))
        list.add(OrderEntity("刀锋舞者", System.currentTimeMillis() + 1000 * 560, 6))
        list.add(OrderEntity("狂野女猎手", System.currentTimeMillis() + 1000 * 1160, 9))
        list.add(OrderEntity("沙漠玫瑰", System.currentTimeMillis() + 1000 * 11160, 11))
        list.add(OrderEntity("封魔剑魂", System.currentTimeMillis() + 1000 * 20060, 23))
        list.add(OrderEntity("残月之肃", System.currentTimeMillis() + 1000 * 122222, 11))
        list.add(OrderEntity("影流之主", System.currentTimeMillis() + 1000 * 888888, 999))

        val split = Tools.splitList(list, 2)
        count_down.init(R.layout.layout_test, 2, split!!, object : ICountDownCallBack {
            override fun updateItemData(view: View, t: Any?) {
                //这里能拿到每个ITEM 和 数据
                if (t is String) {
//                    val tv = view.findViewById<TextView>(R.id.tv_title)
//                    tv.text = t
                }
            }
        })
    }
}
