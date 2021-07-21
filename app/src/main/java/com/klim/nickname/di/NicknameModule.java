package com.klim.nickname.di;

import com.klim.nickname.data.db.dao.NicknameDAO;
import com.klim.nickname.data.db.migrations.Migrations;
import com.klim.nickname.data.repositories.userName.UserNameRepository;
import com.klim.nickname.data.repositories.userName.dataSources.local.LocalDataSource;
import com.klim.nickname.domain.repositories.nickname.UserNameRepositoryI;
import com.klim.nickname.domain.useCases.UsernameUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NicknameModule {

    @Provides
    @Singleton
    public UsernameUseCase provideUsernameUseCase(UserNameRepositoryI repository) {
        return new UsernameUseCase(repository);
    }

    @Provides
    @Singleton
    public UserNameRepositoryI provideUserNameRepository(NicknameDAO nicknameDAO) {
        return new UserNameRepository(new LocalDataSource(nicknameDAO));
    }

}
