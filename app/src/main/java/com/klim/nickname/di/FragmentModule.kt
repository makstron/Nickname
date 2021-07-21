package com.klim.nickname.di

import com.klim.nickname.app.window.generator.GeneratorFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeGeneratorFragment(): GeneratorFragment
}