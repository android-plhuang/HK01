package com.example.hk01.net.di.model

import com.example.hk01.BuildConfig
import com.example.hk01.base.BaseConstant
import com.example.hk01.util.HttpLogger
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * 作者： hpl
 * 创建时间： 2018/8/30
 * email： shipotianhpl@163.com
 * 作用：http的提供类：有okhttp,拦截器，请求头
 */
open class RetrofitFactory private constructor() {

    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val interceptor: Interceptor
    private val retrofit: Retrofit

    //初始化
    init {
        //通用拦截
        interceptor = Interceptor { chain ->
            /*val kv = MMKV.defaultMMKV()
            val token = kv.decodeString(BaseConstant.TOKEN, "")
            val channel = ChannelUtil.getChannel(BaseApplication.context)
            val ip = kv.decodeString(BaseConstant.IP, "")
            val imei = kv.decodeString(BaseConstant.IMEI, "")
            val longitude = kv.decodeString(BaseConstant.LONGITUDE, "")
            val latitude = kv.decodeString(BaseConstant.LATITUDE, "")
            val uid = if (UserInfoUtils.getUserId().toString()=="0") "" else UserInfoUtils.getUserId().toString()
*/
            val heads = Headers.Builder()
                    .add("Content_Type", "application/json")
                    .add("charset", "UTF-8")
//添加请求头
                    /*.add("token", ConfigUtils.getToken())
                    .add("ua", Build.MODEL)
                    .add("ip", ConfigUtils.getIp())
                    .add("version", BuildConfig.VERSION_NAME)
                    .add("channel", ConfigUtils.getChannel())
                    .add("imei", ConfigUtils.getImei())
                    .add("longitude", ConfigUtils.getLongitude())
                    .add("latitude", ConfigUtils.getLatitude())*/
//                    .add("uid", uid)
                    .build()
            val original = chain.request()
            val request = original.newBuilder()
                    .headers(heads)
                    .build()

            chain.proceed(request)
        }
        val baseUrl = if (BuildConfig.DEBUG) BaseConstant.TEST_HTTP_HOST else BaseConstant.CONNECT_HTTP_HOST
//        val baseUrl = "http://149.129.173.10:9001/"
        //Retrofit实例化
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(initClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    }

    /*
        OKHttp创建
     */
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
//                .addNetworkInterceptor(initLogInterceptor())


                .addInterceptor(interceptor)
                .addInterceptor(initLogInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
    }

    /*
        日志拦截器
     */
    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLogger())
//        val interceptor = HttpLoggingInterceptor(com.example.hk01.util.HttpLoggingInterceptor())
        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        HttpLoggingInterceptor()
        return interceptor
    }

    /*
        具体服务实例化
     */
    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

}