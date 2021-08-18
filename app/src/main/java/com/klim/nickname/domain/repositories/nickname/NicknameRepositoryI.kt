package com.klim.nickname.domain.repositories.nickname

import com.klim.nickname.domain.repositories.nickname.models.NicknameEntity

interface NicknameRepositoryI {
    suspend fun save(nicknameEntity: NicknameEntity)

    suspend fun getAll():List<NicknameEntity>
}