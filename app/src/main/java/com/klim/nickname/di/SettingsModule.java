package com.klim.nickname.di;

import com.klim.nickname.data.db.dao.NicknameDAO;
import com.klim.nickname.data.db.dao.SettingsDAO;
import com.klim.nickname.data.repositories.settings.SettingsRepository;
import com.klim.nickname.data.repositories.settings.dataSources.SettingsLocalDataSources;
import com.klim.nickname.data.repositories.userName.UserNameRepository;
import com.klim.nickname.data.repositories.userName.dataSources.local.LocalDataSource;
import com.klim.nickname.domain.settings.SettingsRepositoryI;
import com.klim.nickname.domain.settings.SettingsUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {

    @Provides
    @Singleton
    public SettingsUseCase provideSettingsUseCase(SettingsRepositoryI repository) {
        return new SettingsUseCase(repository);
    }

    @Provides
    @Singleton
    public SettingsRepositoryI provideSettingsRepository(SettingsDAO settingsDAO) {
        return new SettingsRepository(new SettingsLocalDataSources(settingsDAO));
    }
}
