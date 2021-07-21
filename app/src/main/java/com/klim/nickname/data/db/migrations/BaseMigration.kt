package effie.app.com.effie.main.clean.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

abstract class BaseMigration(from: Int, to: Int): Migration(from, to) {
    override fun migrate(database: SupportSQLiteDatabase) {
        println("DbMigration: Room: (${startVersion}, ${endVersion}) started")
        migration(database)
        println("DbMigration: Room: (${startVersion}, ${endVersion}) ended")
    }

    abstract fun migration(database: SupportSQLiteDatabase)
}