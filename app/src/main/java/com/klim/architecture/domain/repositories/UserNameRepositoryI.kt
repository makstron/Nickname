package com.klim.architecture.domain.repositories

import com.klim.architecture.domain.models.UserName

interface UserNameRepositoryI {
    fun save(userName: UserName)

    fun getAll():ArrayList<UserName>
}