package com.klim.nickname.domain.settings

interface SettingsRepositoryI {

    fun getMinLength(): Int
    fun setMinLength(length: Int)

    fun getMaxLength(): Int
    fun setMaxLength(length: Int)

//    fun getAlphabet(): Int
//    fun setAlphabet(): Int
}