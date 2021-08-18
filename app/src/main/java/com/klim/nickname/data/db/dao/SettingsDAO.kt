package com.klim.nickname.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.klim.nickname.data.db.tables.Nickname
import com.klim.nickname.data.db.tables.Setting

@Dao
interface SettingsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun set(setting: Setting)

    @Query("SELECT * FROM settings WHERE `key` = :key LIMIT 1")
    suspend fun get(key: String): Setting

}