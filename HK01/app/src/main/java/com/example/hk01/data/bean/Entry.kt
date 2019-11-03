package com.example.hk01.data.bean

import com.google.gson.annotations.SerializedName

data class Entry(
    /*appBean.icon = it.image[0].label
                appBean.id = it.id.attributes.id
                appBean.name = it.name.label
                appBean.type = it.category.attributes.label
                appBean.summary = it.summary.label*/
    val category: Category,


    val id: Id,
    @SerializedName("im:image")
    val image: List<ImImage>,
    @SerializedName("im:name")
    val name: ImName,
    val summary: Summary


)