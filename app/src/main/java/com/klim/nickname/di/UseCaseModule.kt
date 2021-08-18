package com.klim.nickname.di

import com.klim.nickname.domain.NicknameGenerator
import com.klim.nickname.domain.repositories.nickname.NicknameRepositoryI
import com.klim.nickname.domain.settings.SettingsRepositoryI
import com.klim.nickname.domain.settings.SettingsUseCase
import com.klim.nickname.domain.useCases.NicknameUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideSettingsUseCase(repository: SettingsRepositoryI): SettingsUseCase {
        return SettingsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideNicknameUseCase(repository: NicknameRepositoryI, nicknameGenerator: NicknameGenerator): NicknameUseCase {
        return NicknameUseCase(repository, nicknameGenerator)
    }
}