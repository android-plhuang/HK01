package com.example.hk01.net.http

import com.example.hk01.data.bean.TestBean
import io.reactivex.Flowable

interface HttpHelper {

    fun getList(number:Int): Flowable<TestBean>

    fun getRecommendList(): Flowable<TestBean>


}