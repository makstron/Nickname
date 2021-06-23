package com.klim.architecture.di;

import android.content.Context;

import com.klim.architecture.App;
import com.klim.architecture.data.db.Db;
import com.klim.architecture.data.mappers.UserNameUserNameEntityMapper;
import com.klim.architecture.data.repositories.userName.UserNameDataSourceI;
import com.klim.architecture.data.repositories.userName.UserNameRepository;
import com.klim.architecture.data.repositories.userName.dataSources.LocalDataSource;
import com.klim.architecture.domain.repositories.UserNameRepositoryI;
import com.klim.architecture.domain.useCases.UsernameUseCase;

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
