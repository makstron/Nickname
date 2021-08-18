package com.klim.nickname.domain.useCases

import com.klim.nickname.domain.NicknameGenerator
import com.klim.nickname.domain.repositories.nickname.models.NicknameEntity
import com.klim.nickname.domain.repositories.nickname.UserNameRepositoryI
import com.klim.nickname.utils.UID

class UsernameUseCase
constructor(
    private val repository: UserNameRepositoryI,
    private val nicknameGenerator: NicknameGenerator
) {

    fun getNewUserName(minLength: Int, maxLength: Int, fixedLength: Boolean = false): NicknameEntity {
        val name = nicknameGenerator.createName(minLength, maxLength, fixedLength)
        return NicknameEntity(UID.timeRandomUID(), name, System.currentTimeMillis())
    }

    fun save(nicknameEntity: NicknameEntity) {
        repository.save(nicknameEntity)
    }

    fun getAllSaved(): List<NicknameEntity> {
        return repository.getAll()
    }

}