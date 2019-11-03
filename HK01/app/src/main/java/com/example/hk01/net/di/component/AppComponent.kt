package com.example.hk01.net.di.component

import android.content.Context
import com.example.hk01.net.DataManager
import com.example.hk01.net.di.model.AppModel
import dagger.Component
import javax.inject.Singleton

/**
 * 作者： hpl
 * 创建时间： 2018/8/31
 * email： shipotianhpl@163.com
 * 作用：Application级别的component，就提供一个全局的context;提供一个数据源
 */
@Singleton
@Component(modules = [AppModel::class])
interface AppComponent {
    fun context(): Context
    fun dataManager(): DataManager
}