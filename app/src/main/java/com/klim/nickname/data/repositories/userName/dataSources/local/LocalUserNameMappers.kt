package com.klim.nickname.data.repositories.userName.dataSources.local

import com.klim.nickname.data.db.tables.Nickname
import com.klim.nickname.data.dto.UserNameDTO
import com.klim.nickname.utils.UID

fun UserNameDTO.map() = Nickname(this.id.toByteArray(), this.name, this.time)

fun Nickname.map() = UserNameDTO(UID(this.id), this.nickname, this.createDate)