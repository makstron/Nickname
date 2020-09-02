package com.klim.architecture.data.dto;

import com.klim.architecture.utils.UID;

public class UserNameDTO {
    UID id;
    String name;
    long time;

    public UserNameDTO(String name) {
        this.name = name;
    }

    public UserNameDTO(UID id, String name, long time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public UID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }
}
