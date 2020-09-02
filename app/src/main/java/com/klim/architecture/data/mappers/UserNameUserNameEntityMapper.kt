package com.klim.architecture.data.mappers

import com.klim.architecture.data.dto.UserNameDTO
import com.klim.architecture.domain.models.UserName

class UserNameUserNameEntityMapper {

    fun userNameToUserNameEntity(userName: UserName): UserNameDTO {
        return UserNameDTO(userName.name)
    }

    fun userNameEntityToUserName(userName: UserNameDTO): UserName {
        return UserName(userName.name, userName.time)
    }
}