package com.klim.architecture.data.repositories.userName

import com.klim.architecture.data.mappers.UserNameUserNameEntityMapper
import com.klim.architecture.data.repositories.userName.UserNameDataSourceI
import com.klim.architecture.domain.models.UserName
import com.klim.architecture.domain.repositories.UserNameRepositoryI

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