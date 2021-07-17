package com.klim.nickname.domain.repositories

import com.klim.nickname.domain.models.UserName

interface UserNameRepositoryI {
    fun save(userName: UserName)

    fun getAll():ArrayList<UserName>
}