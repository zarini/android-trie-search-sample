package com.milkyhead.app.androidtriesearchsample.data.datasource.local.entity

import com.google.gson.annotations.SerializedName


data class PersonEntity(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("age") val age: Int
)