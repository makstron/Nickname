package com.klim.nickname.data.db.migrations

import androidx.sqlite.db.SupportSQLiteDatabase
import effie.app.com.effie.main.clean.data.local.migration.BaseMigration

class Migration_1_2 : BaseMigration(1, 2) {

    override fun migration(database: SupportSQLiteDatabase) {
        database.execSQL("""
            CREATE TABLE settings (
                key TEXT PRIMARY KEY NOT NULL,
                value TEXT NOT NULL
            );
        """.trimIndent())
    }
}