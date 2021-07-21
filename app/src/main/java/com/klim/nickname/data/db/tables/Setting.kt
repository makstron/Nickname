package com.klim.nickname.data.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
class Setting(
    @PrimaryKey()
    val key: String,

    val value: String,
) {

}