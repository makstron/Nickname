package com.klim.nickname.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.klim.nickname.app.window.generator.GeneratorViewModel
import com.klim.nickname.app.window.saved.StoreViewModel
import com.klim.nickname.app.window.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GeneratorViewModel::class)
    internal abstract fun generatorViewModel(viewModel: GeneratorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StoreViewModel::class)
    internal abstract fun savedViewModel(viewModel: StoreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun settingsViewModel(viewModel: SettingsViewModel): ViewModel
}