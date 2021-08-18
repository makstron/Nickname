package com.klim.nickname.di

import com.klim.nickname.data.db.dao.NicknameDAO
import com.klim.nickname.data.db.dao.SettingsDAO
import com.klim.nickname.data.repositories.settings.SettingsRepository
import com.klim.nickname.data.repositories.settings.dataSources.SettingsLocalDataSources
import com.klim.nickname.data.repositories.userName.NicknameRepository
import com.klim.nickname.data.repositories.userName.dataSources.local.LocalDataSource
import com.klim.nickname.domain.repositories.nickname.NicknameRepositoryI
import com.klim.nickname.domain.settings.SettingsRepositoryI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNicknameRepository(nicknameDAO: NicknameDAO): NicknameRepositoryI {
        return NicknameRepository(LocalDataSource(nicknameDAO))
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(settingsDAO: SettingsDAO): SettingsRepositoryI {
        return SettingsRepository(SettingsLocalDataSources(settingsDAO))
    }

}