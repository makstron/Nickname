package com.klim.nickname.domain.useCases

import com.klim.nickname.domain.NicknameGenerator
import com.klim.nickname.domain.repositories.nickname.models.NicknameEntity
import com.klim.nickname.domain.repositories.nickname.NicknameRepositoryI
import com.klim.nickname.utils.UID

class NicknameUseCase
constructor(
    private val repository: NicknameRepositoryI,
    private val nicknameGenerator: NicknameGenerator
) {

    fun getNewUserName(minLength: Int, maxLength: Int, fixedLength: Boolean = false): NicknameEntity {
        val name = nicknameGenerator.createName(minLength, maxLength, fixedLength)
        return NicknameEntity(UID.timeRandomUID(), name, System.currentTimeMillis())
    }

    suspend fun save(nicknameEntity: NicknameEntity) {
        repository.save(nicknameEntity)
    }

    suspend fun getAllSaved(): List<NicknameEntity> {
        return repository.getAll()
    }

}