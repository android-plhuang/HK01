package com.example.hk01.net.http

import com.example.hk01.data.bean.TestBean
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    /*邮箱登录接口*/
/*@FormUrlEncoded
@POST("HAccount/EmailLogin")
fun emailLogin(@Field("email") email:String,@Field("password") password:String): Flowable<UserInfo>*/
    /**
     * 获取列表数据
     */
    @GET("/hk/rss/topfreeapplications/limit={number}/json")
    fun getList(@Path("number") number:Int): Flowable<TestBean>
    /**
     * 获取推荐列表数据
     */
    @GET("/hk/rss/topgrossingapplications/limit=10/json")
    fun getRecommendList(): Flowable<TestBean>


}