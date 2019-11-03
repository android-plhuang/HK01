package  com.example.hk01.net.di.scope

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/**
 * 作者： hpl
 * 创建时间： 2018/8/31
 * email： shipotianhpl@163.com
 * 作用：
 */
/*
    组件级别 作用域
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class PerComponentScope