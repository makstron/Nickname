package com.klim.nickname.di;

import android.content.Context;

import com.klim.nickname.App;

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

}
