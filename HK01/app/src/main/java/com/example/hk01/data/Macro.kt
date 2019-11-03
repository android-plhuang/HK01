package com.example.hk01.data

/**
 * 作者： hpl
 * 创建时间： 2019/10/31
 * email： shipotianhpl@163.com
 * 作用：
 */
class Macro {
    companion object {

        const val CONNECT_HOST = "https://itunes.apple.com"//正式请求地址


//    const val CONNECT_TEST_HOST = "http://192.168.0.135:9091"//测试请求地址

        const val CONNECT_TEST_HOST = "https://itunes.apple.com"//测试请求地址


        const val CODE_FAIL = 0//失败状态码


        const val CODE_SUCCESS = 1//成功状态码
        const val PAGE_SIZE = 10//每一页的条数
        const val DB_NAME = "app_data"


    }
}