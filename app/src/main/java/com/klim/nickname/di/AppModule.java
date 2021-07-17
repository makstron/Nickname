package com.klim.nickname.di;

import android.content.Context;

import com.klim.nickname.App;
import com.klim.nickname.data.db.Db;
import com.klim.nickname.data.mappers.UserNameUserNameEntityMapper;
import com.klim.nickname.data.repositories.userName.UserNameDataSourceI;
import com.klim.nickname.data.repositories.userName.UserNameRepository;
import com.klim.nickname.data.repositories.userName.dataSources.LocalDataSource;
import com.klim.nickname.domain.repositories.UserNameRepositoryI;
import com.klim.nickname.domain.useCases.UsernameUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

//    @Binds
//    public abstract Application bindApplication(App app);

    @Provides
    @Singleton
    public Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    public App provideApp() {
        return app;
    }

    @Provides
    @Singleton
    public Db provideDb() {
        return new Db(app);
    }

    @Provides
    @Singleton
    public UsernameUseCase provideUsernameUseCase(UserNameRepositoryI repository) {
        return new UsernameUseCase(repository);
    }

    @Provides
    @Singleton
    public UserNameRepositoryI provideUserNameRepository(Db db) {
        return new UserNameRepository(new LocalDataSource(db), new UserNameUserNameEntityMapper());
    }

    @Provides
    @Singleton
    public UserNameUserNameEntityMapper provideUserNameUserNameEntityMapper() {
        return new UserNameUserNameEntityMapper();
    }
}
