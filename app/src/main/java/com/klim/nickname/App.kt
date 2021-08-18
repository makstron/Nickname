package com.klim.nickname

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.klim.nickname.data.db.AppDatabase
import com.klim.nickname.data.db.migrations.Migration_1_2
import com.klim.nickname.data.db.migrations.Migrations
import com.klim.nickname.di.AppComponent
import com.klim.nickname.di.AppModule
import com.klim.nickname.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
//            .addModule(ViewModelFactoryModule())
            .build()
    }

    val db: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, AppDatabase.DB_NAME)
//            .allowMainThreadQueries()
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .addMigrations(*Migrations.getAll())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}