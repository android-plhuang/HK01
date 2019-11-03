package com.example.hk01.net.di.component

import android.app.Activity
import android.content.Context
import com.example.hk01.ui.activity.MainActivity
import com.example.hk01.net.di.model.ActivityModule
import com.example.hk01.net.di.model.LifecycleProviderModule
import com.example.hk01.net.di.scope.ActivityScope
import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Component

/**/
/**
 * 作者： hpl
 * 创建时间： 2018/8/31
 * email： shipotianhpl@163.com
 * 作用：ActivityComponent,不是单利的，提供activity，Lifecycle，由于是一级一级的获取，所以要提供出context
 */
@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class, LifecycleProviderModule::class])
interface ActivityComponent {
    fun activity(): Activity
    fun context(): Context
    fun lifecycleProvider(): LifecycleProvider<*>
    fun inject(mainActivity: MainActivity)
//    fun inject(aboutUsActivity: AboutUsActivity)


}