package com.klim.nickname.ui.entities.helpers

import com.klim.nickname.ui.entities.UserNameEntity

object UserNameH {
    fun clone(nameEntity: UserNameEntity): UserNameEntity {
        return UserNameEntity(nameEntity.name, nameEntity.time)
    }
}