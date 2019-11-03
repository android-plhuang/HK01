package com.example.hk01.net

import com.example.hk01.data.bean.TestBean
import com.example.hk01.net.http.HttpHelper
import io.reactivex.Flowable

class DataManager(private val httpHelper: HttpHelper) : HttpHelper {
    /*邮箱登录接口*/
    /* override fun emailLogin(email:String,password:String): Flowable<UserInfo> {
         return httpHelper.emailLogin(email,password)
     }*/


    override fun getList(number:Int): Flowable<TestBean> {
        return httpHelper.getList(number)
    }

    override fun getRecommendList(): Flowable<TestBean> {
        return httpHelper.getRecommendList()
    }
}