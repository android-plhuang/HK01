package com.example.hk01.net.http

import com.example.hk01.data.bean.TestBean
import dagger.Module
import io.reactivex.Flowable

@Module
open class RetrofitHttpHelperImp(private val api: Api) : HttpHelper {
    /*邮箱登录接口*/
/*override fun emailLogin(email:String,password:String): Flowable<UserInfo> {
return api.emailLogin(email,password)
}*/
    override fun getList(number:Int): Flowable<TestBean> {
        return api.getList(number)
    }

    override fun getRecommendList(): Flowable<TestBean> {
        return api.getRecommendList()
    }
}