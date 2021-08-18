package com.klim.nickname.data.repositories.userName.dataSources.local

import com.klim.nickname.data.db.dao.NicknameDAO
import com.klim.nickname.data.dto.UserNameDTO
import com.klim.nickname.data.repositories.userName.UserNameDataSourceI
import javax.inject.Inject

class LocalDataSource
@Inject
constructor(private val nicknameDAO: NicknameDAO) : UserNameDataSourceI {

    override suspend fun save(userName: UserNameDTO) {
        nicknameDAO.insert(userName.map())
    }

    override suspend fun getAll(): List<UserNameDTO> {
        val entities = ArrayList<UserNameDTO>()
        entities.addAll(nicknameDAO.getAll().map { it.map() })
        return entities
    }
}