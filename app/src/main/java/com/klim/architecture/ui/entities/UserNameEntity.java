package com.klim.architecture.ui.entities;

public class UserNameEntity {
    String name;
    String time;

    public UserNameEntity(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
