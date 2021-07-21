package com.klim.nickname.data.repositories.userName

import com.klim.nickname.data.repositories.userName.mappers.map
import com.klim.nickname.domain.repositories.nickname.models.NicknameEntity
import com.klim.nickname.domain.repositories.nickname.UserNameRepositoryI

class UserNameRepository(private val localStore: UserNameDataSourceI) : UserNameRepositoryI {

    override fun save(nicknameEntity: NicknameEntity) {
        localStore.save(nicknameEntity.map())
    }

    override fun getAll(): ArrayList<NicknameEntity> {
        val userNames = ArrayList<NicknameEntity>()
        localStore.getAll().forEach {
            userNames.add(it.map())
        }
        return userNames
    }

}