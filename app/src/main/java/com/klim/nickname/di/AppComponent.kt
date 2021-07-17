package com.klim.nickname.di

import com.klim.nickname.App
import com.klim.nickname.di.generator.GeneratorComponent
import com.klim.nickname.di.generator.GeneratorModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    fun inject(app: App?)

    fun getGeneratorComponent(module: GeneratorModule): GeneratorComponent
}