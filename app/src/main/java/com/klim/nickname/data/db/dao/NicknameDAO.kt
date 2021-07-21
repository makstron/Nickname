package com.klim.nickname.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.klim.nickname.data.db.tables.Nickname

@Dao
interface NicknameDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nickname: Nickname)

//    @Query("INSERT INTO nicknames (nickname, create_date) VALUES (:nickname., nickname) ")
//    fun add(nickname: Nickname)

    @Query("SELECT * FROM nicknames ORDER BY create_date DESC")
    fun getAll(): List<Nickname>

}