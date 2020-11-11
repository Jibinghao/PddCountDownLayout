package com.jibinghao.mylibrary.util

/**
 * FileName：Tools
 * Description：
 * Copyright
 */
object Tools {
    //将一个list拆分成多个list
    fun <T> splitList(
        originList: List<T>,
        everySize: Int
    ): List<List<T>>? {
        val ret: MutableList<List<T>> =
            ArrayList()
        if (!isEmpty(originList)) {
            var size = originList.size
            var start = 0
            while (size > everySize) {
                ret.add(originList.subList(start, start + everySize))
                size -= everySize
                start += everySize
            }
            if (size > 0) {
                ret.add(originList.subList(start, originList.size))
            }
        } else {
            ret.add(originList)
        }
        return ret
    }

    fun isEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }
}