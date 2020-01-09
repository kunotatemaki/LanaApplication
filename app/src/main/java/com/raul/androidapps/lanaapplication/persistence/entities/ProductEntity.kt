package com.raul.androidapps.lanaapplication.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.raul.androidapps.lanaapplication.domain.Item

@Entity(tableName = "product", indices = [(Index(value = arrayOf("code"), unique = true))])
data class ProductEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "code")
    val code: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "price")
    var price: Double
){
    fun toItem(): Item =
        Item(code = code, name = name, price = price)

    companion object {
        fun from(item: Item): ProductEntity =
            ProductEntity(code = item.code, name = item.name, price = item.price)
    }
}

