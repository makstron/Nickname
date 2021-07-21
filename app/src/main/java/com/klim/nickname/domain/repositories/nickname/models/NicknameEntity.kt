package com.klim.nickname.domain.repositories.nickname.models

import com.klim.nickname.utils.UID

class NicknameEntity(
    val id: UID,
    var name: String,
    var time: Long
) {
}