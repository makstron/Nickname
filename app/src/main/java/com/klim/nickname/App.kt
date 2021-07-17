package com.klim.nickname

import android.app.Application
import com.klim.nickname.data.db.Db
import com.klim.nickname.di.AppComponent
import com.klim.nickname.di.AppModule
import com.klim.nickname.di.DaggerAppComponent
import com.klim.nickname.di.ViewModelFactoryModule

class App : Application() {

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
//            .addModule(ViewModelFactoryModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}