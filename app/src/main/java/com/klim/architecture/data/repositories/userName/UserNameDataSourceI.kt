package com.klim.architecture.data.repositories.userName

import com.klim.architecture.data.dto.UserNameDTO

interface UserNameDataSourceI {
    fun save(userName: UserNameDTO)

    fun getAll():ArrayList<UserNameDTO>
}