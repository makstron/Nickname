package com.klim.nickname.data.db;

public class MigrationCreateScripts {
    public static final String CREATE_USER_NAMES_TABLE = "DROP TABLE IF EXISTS UserNames; " +
            "CREATE TABLE [UserNames] (\n" +
            " [id]        BLOB(16),\n" +
            " [userName]  VARCHAR,\n" +
            " [saveTime]  BIGINT,\n" +
            " PRIMARY KEY (id))";
}
