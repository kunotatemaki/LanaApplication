package com.raul.androidapps.lanaapplication.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.raul.androidapps.lanaapplication.network.AppApi.Item

@Entity(tableName = "product", indices = [(Index(value = arrayOf("code"), unique = true))])
data class ProductEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "code")
    val code: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "price")
    var price: Double
)

