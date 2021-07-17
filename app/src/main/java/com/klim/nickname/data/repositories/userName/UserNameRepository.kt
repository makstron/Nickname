package com.klim.nickname.data.repositories.userName

import com.klim.nickname.data.mappers.UserNameUserNameEntityMapper
import com.klim.nickname.data.repositories.userName.UserNameDataSourceI
import com.klim.nickname.domain.models.UserName
import com.klim.nickname.domain.repositories.UserNameRepositoryI

class UserNameRepository(val localStore: UserNameDataSourceI, val mapper: UserNameUserNameEntityMapper) : UserNameRepositoryI {

    override fun save(userName: UserName) {
        localStore.save(mapper.userNameToUserNameEntity(userName))
    }

    override fun getAll(): ArrayList<UserName> {
        var userNames = ArrayList<UserName>()

        localStore.getAll().forEach {
            userNames.add(mapper.userNameEntityToUserName(it))
        }

        return userNames
    }

}