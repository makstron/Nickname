package com.klim.nickname.data.repositories.userName

import com.klim.nickname.data.dto.UserNameDTO

interface UserNameDataSourceI {
    suspend fun save(userName: UserNameDTO)

    suspend fun getAll():List<UserNameDTO>
}