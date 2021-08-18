package com.klim.nickname.domain.settings


class SettingsUseCase constructor(private val repository: SettingsRepositoryI) {

    suspend fun getMinLength(): Int = repository.getMinLength()

    suspend fun setMinLength(length: Int) {
        repository.setMinLength(length)
    }

    /////

    suspend fun getMaxLength(): Int = repository.getMaxLength()

    suspend fun setMaxLength(length: Int) {
        repository.setMaxLength(length)
    }

}