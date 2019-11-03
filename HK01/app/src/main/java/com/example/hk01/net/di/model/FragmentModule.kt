package com.example.hk01.net.di.model


import android.support.v4.app.Fragment
import com.example.hk01.net.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * 作者： hpl
 * 创建时间： 2018/8/31
 * email： shipotianhpl@163.com
 * 作用：activity 级别的model
 */
@Module
class FragmentModule(private val fragment: Fragment) {
    @ActivityScope
    @Provides
    fun providerActivity(): Fragment {
        return fragment
    }
}