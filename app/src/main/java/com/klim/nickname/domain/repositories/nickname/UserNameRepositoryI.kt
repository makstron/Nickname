package com.klim.nickname.domain.repositories.nickname

import com.klim.nickname.domain.repositories.nickname.models.NicknameEntity

interface UserNameRepositoryI {
    fun save(nicknameEntity: NicknameEntity)

    fun getAll():ArrayList<NicknameEntity>
}