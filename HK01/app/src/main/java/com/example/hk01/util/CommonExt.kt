package com.example.hk01.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.hk01.util.glide.GlideRoundTransform
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit

/**
 * 作者： hpl
 * 创建时间： 2019/11/2
 * email： shipotianhpl@163.com
 * 作用：kotlin常用的扩展
 */
/**
 *加载圆角图片，正常的路径
 */
fun ImageView.loadImage(context:Context,photo: Any, angle: Int) {
    val myOptions = RequestOptions()
        .transform(GlideRoundTransform(context, angle))
    Glide.with(context).load(photo).apply(myOptions).into(this)
}
/**
 *加载圆形图片，正常的路径
 */
fun ImageView.loadCircleImage(context:Context,photo: Any) {
   /* val myOptions = RequestOptions()
        .transform(Glide)*/
    Glide.with(context).load(photo).apply(RequestOptions.bitmapTransform( CircleCrop())).into(this)
}

/**
 * 一秒之内只能点击一次
 */
@SuppressLint("CheckResult")
fun View.clickView(methor: () -> Unit) {
    RxView.clicks(this).throttleFirst(1, TimeUnit.SECONDS).subscribe {
        methor()
    }

}