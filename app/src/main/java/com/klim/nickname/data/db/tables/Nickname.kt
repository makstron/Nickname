package com.klim.nickname.data.db.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nicknames")
class Nickname(
    @PrimaryKey()
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val id: ByteArray,

    val nickname: String,

    @ColumnInfo(name = "create_date")
    val createDate: Long
) {

}