package com.klim.nickname.data.repositories.settings

import com.klim.nickname.data.db.tables.Setting
import com.klim.nickname.domain.settings.SettingsRepositoryI
import com.klim.nickname.domain.settings.enums.SettingKeys

class SettingsRepository(private val dataSource: SettingDataSourceI) : SettingsRepositoryI {

    override fun getMinLength(): Int = dataSource.getSetting(SettingKeys.MIN_LENGTH).value.toInt()

    override fun setMinLength(length: Int) {
        dataSource.setSetting(Setting(SettingKeys.MIN_LENGTH.key, length.toString()))
    }

    override fun getMaxLength(): Int = dataSource.getSetting(SettingKeys.MAX_LENGTH).value.toInt()

    override fun setMaxLength(length: Int) {
        dataSource.setSetting(Setting(SettingKeys.MAX_LENGTH.key, length.toString()))
    }

}