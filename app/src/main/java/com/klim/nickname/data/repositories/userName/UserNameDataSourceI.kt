package com.klim.nickname.data.repositories.userName

import com.klim.nickname.data.dto.UserNameDTO

interface UserNameDataSourceI {
    fun save(userName: UserNameDTO)

    fun getAll():List<UserNameDTO>
}