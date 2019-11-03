package com.example.hk01.data.bean

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * 作者： hpl
 * 创建时间： 2019/11/2
 * email： shipotianhpl@163.com
 * 作用：应用的信息，解析过来的数据量太多，不方便存入数据库
 */
@RealmClass
open class ApplicationBean : RealmModel {
    @PrimaryKey
    var id: String = ""
    var name: String? = null
    var type: String? = null
    var icon: String? = null
    var summary: String? = null
}