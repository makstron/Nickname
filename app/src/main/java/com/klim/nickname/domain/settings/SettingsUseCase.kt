package com.klim.nickname.domain.settings


class SettingsUseCase constructor(private val repository: SettingsRepositoryI) {

    fun getMinLength(): Int = repository.getMinLength()

    fun setMinLength(length: Int) {
        repository.setMinLength(length)
    }

    /////

    fun getMaxLength(): Int = repository.getMaxLength()

    fun setMaxLength(length: Int) {
        repository.setMaxLength(length)
    }

}