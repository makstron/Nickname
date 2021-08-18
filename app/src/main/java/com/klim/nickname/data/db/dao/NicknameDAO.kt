package com.klim.nickname.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.klim.nickname.data.db.tables.Nickname

@Dao
interface NicknameDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nickname: Nickname)

    @Query("SELECT * FROM nicknames ORDER BY create_date DESC")
    suspend fun getAll(): List<Nickname>

}