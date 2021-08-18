package com.klim.nickname.app.window.generator.entity_view

import com.klim.nickname.domain.repositories.nickname.models.NicknameEntity

class UserNameEntityView(
    var name: String,
    var isSaved: Boolean = false,
    var nickname: NicknameEntity? = null,
) {
    constructor(nickname: NicknameEntity) : this(nickname.name, false, nickname)
}