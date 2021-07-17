package com.klim.nickname.data.repositories.userName.dataSources

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.klim.nickname.data.db.Db
import com.klim.nickname.data.dto.UserNameDTO
import com.klim.nickname.data.repositories.userName.UserNameDataSourceI
import com.klim.nickname.utils.UID
import java.lang.Exception
import javax.inject.Inject


class LocalDataSource
@Inject
constructor(val db: Db) : UserNameDataSourceI {

    override fun save(userName: UserNameDTO) {
        val sqlInsertPromo = "INSERT OR REPLACE INTO UserNames (id, userName, saveTime) " +
                "VALUES (?, ?, ?)"

        val bindArgs = arrayOf<Any>(UID.timeRandomUID().toByteArray(), userName.name, System.currentTimeMillis())

        val db: SQLiteDatabase = db.writableDatabase
        db.beginTransaction()
        db.execSQL(sqlInsertPromo, bindArgs)
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    override fun getAll(): ArrayList<UserNameDTO> {
        var entities = ArrayList<UserNameDTO>()

        var cursor: Cursor? = null
        try {
            cursor = db.selectSQL("SELECT * FROM UserNames ORDER BY saveTime DESC")
            cursor?.let { cursor ->
                if (cursor.moveToFirst()) {
                    var idIdx = cursor.getColumnIndex("id")
                    var nameIdx = cursor.getColumnIndex("userName")
                    var saveTimeIdx = cursor.getColumnIndex("saveTime")
                    do {
                        var une = UserNameDTO(UID(cursor.getBlob(idIdx)), cursor.getString(nameIdx), cursor.getLong(saveTimeIdx))
                        entities.add(une)
                    } while (cursor.moveToNext())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.let {
                if (!it.isClosed) {
                    it.close()
                }
            }
        }
        return entities
    }
}