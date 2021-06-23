package com.klim.architecture.di

import com.klim.architecture.App
import com.klim.architecture.di.generator.GeneratorComponent
import com.klim.architecture.di.generator.GeneratorModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    fun inject(app: App?)

    fun getGeneratorComponent(module: GeneratorModule): GeneratorComponent
}