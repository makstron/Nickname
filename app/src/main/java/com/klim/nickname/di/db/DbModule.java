package com.klim.nickname.di.db;

import com.klim.nickname.App;
import com.klim.nickname.data.db.AppDatabase;
import com.klim.nickname.data.db.dao.NicknameDAO;
import com.klim.nickname.data.db.dao.SettingsDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    public AppDatabase provideDatabase(App app) {
        return app.getDb();
    }

    @Provides
    @Singleton
    public NicknameDAO getNicknameDAO(AppDatabase db) {
        return db.getNicknameDao();
    }

    @Provides
    @Singleton
    public SettingsDAO getSettingsDAO(AppDatabase db) {
        return db.getSettingsDao();
    }
}
