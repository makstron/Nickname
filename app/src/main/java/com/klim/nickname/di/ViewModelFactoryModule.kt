package com.klim.nickname.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.klim.nickname.ui.generator.GeneratorViewModel
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
    internal abstract fun demoViewModel(viewModel: GeneratorViewModel): ViewModel

}