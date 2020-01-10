package com.raul.androidapps.lanaapplication.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.raul.androidapps.lanaapplication.network.AppApi.Item

@Entity(tableName = "basket", indices = [(Index(value = arrayOf("code"), unique = true))])
data class BasketEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "code")
    val code: String,
    @ColumnInfo(name = "selections")
    var selections: Int
)

