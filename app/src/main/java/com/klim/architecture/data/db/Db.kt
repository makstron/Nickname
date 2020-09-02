package com.klim.architecture.data.db;

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.text.TextUtils
import android.util.Log
//import com.klim.architecture.MyLogs
import com.klim.architecture.data.db.Migrations
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class Db(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, Migrations.DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "db.db"
    }

    private var haveOpenTransaction = false

//    companion object {

//        private var _instance: Db? = null

//        fun getInstance(): Db {
//            if (_instance == null) {
//                _instance = Db(DbMakerApplication.context, Migrations.DATABASE_VERSION)
//            }
//            return _instance!!
//        }

        fun onAppStart(context: Context) {
            if (!checkDbExist(context)) {
                installDatabaseFromAssets(context)
            }
        }

        private fun checkDbExist(context: Context): Boolean {
            val dbPath = context.getDatabasePath(DATABASE_NAME).path
            val dbFile = File(dbPath)

            if (!dbFile.exists()) {
                return false
            }

            var dbExist = false
            var checkDB: SQLiteDatabase? = null
            try {
                try {
                    checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                }
                checkDB?.close()
                dbExist = checkDB != null
            } catch (ioe: IOException) {
                ioe.printStackTrace()
            } finally {
                checkDB?.close()
            }

            return dbExist
        }

        private fun installDatabaseFromAssets(context: Context) {
            val inputStream = context.assets.open("$DATABASE_NAME")

            try {
                File(context.getDatabasePath("$DATABASE_NAME").path.replace("$DATABASE_NAME", "")).mkdir()
                val outputFile = File(context.getDatabasePath("$DATABASE_NAME").path)
                val outputStream = FileOutputStream(outputFile)

                inputStream.copyTo(outputStream)
                inputStream.close()

                outputStream.flush()
                outputStream.close()
            } catch (exception: Throwable) {
                exception.printStackTrace()
            }
        }

//    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("Database", "onCreate")
        for (i in 1..Migrations.DATABASE_VERSION) {
//            onUpgrade(db, i - 1, i)
            Migrations.getMigration(this, db, i)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d("Database", "onUpgrade from $oldVersion to $newVersion")
        try {
            if (newVersion > oldVersion) {
                for (i in oldVersion + 1..newVersion) {
                    Migrations.getMigration(this, db, i)
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun execScripts(db: SQLiteDatabase, script: String) {
        try {
            val queries = script.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var query: String
            for (_q in queries) {
                query = _q.trim()
                if (!TextUtils.isEmpty(query)) {
                    db.execSQL(query)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun selectSQL(sql: String?): Cursor? {
        var result: Cursor? = null
        try {
            val db = this.writableDatabase
            if (db != null) {
                result = selectSQL(db, sql)
            } else {
                Log.e("Db.selectSQL", "getWritableDatabase() returned null. Query:" + (sql ?: ""))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return result
    }

    private fun selectSQL(db: SQLiteDatabase, sql: String?): Cursor? {
//        MyLogs.printDbLogsSql(sql)
        var curs: Cursor? = null
        try {
            curs = db.rawQuery(sql, null)
            curs!!.moveToFirst()
        } catch (ex: SQLException) {
            ex.printStackTrace()
            Log.e("Db.selectSQL", ex.toString() + " Query:" + (sql ?: ""))
        }
//        MyLogs.printDurationRequest()
        return curs
    }

    //in transaction

    @Throws(SQLException::class)
    fun execSQL(sql: String) {
        val db = writableDatabase
        execSQL(db, sql, null)
    }

    @Throws(SQLException::class)
    fun execSQL(sql: String, bindArgs: Array<Any>) {
        val db = writableDatabase
        execSQL(db, sql, bindArgs)
    }

    private fun execSQL(db: SQLiteDatabase, sql: String, bindArgs: Array<Any>?) {
//        MyLogs.printDbLogs("EXEC_SQL_WITH_BIND_ARGS")

        if (!haveOpenTransaction) {
            db.beginTransaction()
        }
        try {
            if (bindArgs == null) {
//                MyLogs.printDbLogsSql(sql)
                db.execSQL(sql)
//                MyLogs.printDurationRequest()
            } else {
//                MyLogs.printDbLogsSql(sql, bindArgs)
                db.execSQL(sql, bindArgs)
//                MyLogs.printDurationRequest()
            }
            if (!haveOpenTransaction)
                db.setTransactionSuccessful()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (!haveOpenTransaction)
                db.endTransaction()
        }
    }

    //in transaction

    //without transaction

    @Throws(SQLException::class)
    fun execScriptsInOneTransaction(script: String) {
        val db = writableDatabase
//        MyLogs.printDbLogs("EXEC_SQL_SCRIPTS")

        if (!haveOpenTransaction) {
            db.beginTransaction()
        }
        try {
//            MyLogs.printDbLogsSql(script)
            var cleanScripts = script.replace("\n", " ")
            val queries = cleanScripts.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (query in queries) {
                if (query.length > 1) {
                    db.execSQL(query)
                }
            }

//            MyLogs.printDurationRequest()

            if (!haveOpenTransaction)
                db.setTransactionSuccessful()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (!haveOpenTransaction)
                db.endTransaction()
        }
    }

    //without transaction

    fun getDataLongValue(sql: String, defaultValue: Long): Long {
        val cursor = selectSQL(sql)

        if (cursor != null && cursor.count != 0 && cursor.moveToPosition(0)) {
            val value = cursor.getLong(0)
            cursor.close()
            return value
        }

        cursor?.close()

        return defaultValue
    }

    fun getDataIntValue(sql: String, defaultValue: Int): Int {
        val cursor = selectSQL(sql)

        if (cursor != null && cursor.count != 0 && cursor.moveToPosition(0)) {
            val value = cursor.getInt(0)
            cursor.close()
            return value
        }

        cursor?.close()

        return defaultValue
    }

    fun getDataStringValue(sql: String, defaultValue: String): String {
        val cursor = selectSQL(sql)
        if (cursor != null && cursor.count != 0 && cursor.moveToPosition(0)) {
            val value = cursor.getString(0)
            cursor.close()
            return value
        }
        cursor?.close()
        return defaultValue
    }

    fun getBlob(sql: String): ByteArray {
        val cursor = selectSQL(sql)
        if (cursor != null && cursor.count != 0 && cursor.moveToPosition(0)) {
            val value = cursor.getBlob(0)
            cursor.close()
            return value
        }
        cursor?.close()
        return ByteArray(0)
    }
}