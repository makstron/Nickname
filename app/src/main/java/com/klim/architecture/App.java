package com.klim.architecture;

import android.app.Application;

import com.klim.architecture.data.db.Db;

public class App extends Application {

    private Db db;

    @Override
    public void onCreate() {
        super.onCreate();

        db = new Db(this);
    }

    public Db getDb() {
        return db;
    }
}
