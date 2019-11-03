package com.example.hk01.net.di.model

import android.content.Context
import  com.example.hk01.base.BaseApplication
import  com.example.hk01.net.DataManager
import  com.example.hk01.net.http.Api
import  com.example.hk01.net.http.HttpHelper
import  com.example.hk01.net.http.RetrofitHttpHelperImp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * 作者： hpl
 * 创建时间： 2018/8/30
 * email： shipotianhpl@163.com
 * 作用：appModel
 */
@Module
class AppModel(private val application: BaseApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideDataManager(httpHelper: HttpHelper): DataManager {
        return DataManager(httpHelper)
    }


    @Provides
    @Singleton
    fun provideHttpHelper(): HttpHelper {
        return RetrofitHttpHelperImp(RetrofitFactory.instance.create(Api::class.java))
    }


}