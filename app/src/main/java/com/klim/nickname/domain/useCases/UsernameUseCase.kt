package com.klim.nickname.domain.useCases

import com.klim.nickname.domain.NicknameGenerator
import com.klim.nickname.domain.repositories.nickname.models.NicknameEntity
import com.klim.nickname.domain.repositories.nickname.UserNameRepositoryI
import com.klim.nickname.utils.UID

class UsernameUseCase
constructor(private val repositoryI: UserNameRepositoryI) {

    fun getNewUserName(minLength: Int, maxLength: Int, fixedLength: Boolean = false): NicknameEntity {
        val name = NicknameGenerator.createName(minLength, maxLength, fixedLength)
        return NicknameEntity(UID.timeRandomUID(), name, System.currentTimeMillis())
    }

    fun save(nicknameEntity: NicknameEntity) {
        repositoryI.save(nicknameEntity)
    }

    fun getAllSaved(): ArrayList<NicknameEntity> {
        return repositoryI.getAll()
    }

}