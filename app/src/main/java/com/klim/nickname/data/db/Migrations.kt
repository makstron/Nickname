package com.klim.nickname.data.db;

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.klim.nickname.data.db.MigrationCreateScripts.*

object Migrations {
    const val DATABASE_VERSION = 2

    internal fun getMigration(db: Db, sqlDb: SQLiteDatabase, version: Int) {
        Log.d("Database", "getMigration $version")
        when (version) {
            2 -> {
                db.execScripts(sqlDb, CREATE_USER_NAMES_TABLE)
            }
//            3 -> {
//                sqlDb.execSQL("ALTER TABLE Tables ADD colorTitle VARCHAR;")
//            }
        }
    }
}