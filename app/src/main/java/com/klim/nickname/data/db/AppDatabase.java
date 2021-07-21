package com.klim.nickname.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.klim.nickname.data.db.dao.NicknameDAO;
import com.klim.nickname.data.db.dao.SettingsDAO;
import com.klim.nickname.data.db.tables.Nickname;
import com.klim.nickname.data.db.tables.Setting;

@Database(entities = {Nickname.class, Setting.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "nickname.db";

    public abstract NicknameDAO getNicknameDao();
    public abstract SettingsDAO getSettingsDao();
}