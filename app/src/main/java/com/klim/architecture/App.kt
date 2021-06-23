package com.klim.architecture

import android.app.Application
import com.klim.architecture.data.db.Db
import com.klim.architecture.di.AppComponent
import com.klim.architecture.di.AppModule
import com.klim.architecture.di.DaggerAppComponent
import com.klim.architecture.di.ViewModelFactoryModule

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