package com.klim.architecture.ui.entities.helpers

import com.klim.architecture.ui.entities.UserNameEntity

object UserNameH {
    fun clone(nameEntity: UserNameEntity): UserNameEntity {
        return UserNameEntity(nameEntity.name, nameEntity.time)
    }
}