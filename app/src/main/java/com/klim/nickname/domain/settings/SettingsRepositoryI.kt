package com.klim.nickname.domain.settings

interface SettingsRepositoryI {

    suspend fun getMinLength(): Int
    suspend fun setMinLength(length: Int)

    suspend fun getMaxLength(): Int
    suspend fun setMaxLength(length: Int)

}