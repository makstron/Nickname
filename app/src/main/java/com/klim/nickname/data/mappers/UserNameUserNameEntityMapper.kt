package com.klim.nickname.data.mappers

import com.klim.nickname.data.dto.UserNameDTO
import com.klim.nickname.domain.models.UserName

class UserNameUserNameEntityMapper {

    fun userNameToUserNameEntity(userName: UserName): UserNameDTO {
        return UserNameDTO(userName.name)
    }

    fun userNameEntityToUserName(userName: UserNameDTO): UserName {
        return UserName(userName.name, userName.time)
    }
}