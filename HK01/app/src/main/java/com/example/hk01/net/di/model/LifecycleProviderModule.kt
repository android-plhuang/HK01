package com.example.hk01.net.di.model

import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Module
import dagger.Provides

/**
 * 作者： hpl
 * 创建时间： 2018/8/31
 * email： shipotianhpl@163.com
 * 作用：
 */
@Module
class LifecycleProviderModule(private val lifecycle: LifecycleProvider<*>) {
    @Provides
    fun provideLifecycle(): LifecycleProvider<*> {
        return lifecycle
    }
}