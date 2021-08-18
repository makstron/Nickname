package com.klim.nickname.data.repositories.userName.mappers

import com.klim.nickname.data.dto.UserNameDTO
import com.klim.nickname.domain.repositories.nickname.models.NicknameEntity

public fun NicknameEntity.map() = UserNameDTO(this.id, this.name, this.time)

fun UserNameDTO.map() = NicknameEntity(this.id, this.name, this.time)