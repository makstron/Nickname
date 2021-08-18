package com.klim.nickname.di

import com.klim.nickname.App
import com.klim.nickname.di.db.DbModule
import com.klim.nickname.di.generator.GeneratorComponent
import com.klim.nickname.di.generator.GeneratorModule
import com.klim.nickname.di.saved.SavedComponent
import com.klim.nickname.di.saved.SavedModule
import com.klim.nickname.di.settings.SettingsComponent
import com.klim.nickname.di.settings.SettingsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DbModule::class,
        ViewModelFactoryModule::class,
        UseCaseModule::class,
        RepositoryModule::class,
    ]
)
interface AppComponent {

    fun inject(app: App?)

    fun getGeneratorComponent(module: GeneratorModule): GeneratorComponent

    fun getSavedComponent(module: SavedModule): SavedComponent

    fun getSettingsComponent(module: SettingsModule): SettingsComponent
}