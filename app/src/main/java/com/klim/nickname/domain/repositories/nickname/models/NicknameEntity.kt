package com.klim.nickname.domain.repositories.nickname.models

import com.klim.nickname.utils.UID

data class NicknameEntity(
    val id: UID,
    val name: String,
    var time: Long
) {
}