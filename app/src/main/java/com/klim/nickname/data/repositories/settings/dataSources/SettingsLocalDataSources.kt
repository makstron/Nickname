package com.klim.nickname.data.repositories.settings.dataSources

import com.klim.nickname.data.db.dao.SettingsDAO
import com.klim.nickname.data.db.tables.Setting
import com.klim.nickname.data.repositories.settings.SettingDataSourceI
import com.klim.nickname.domain.settings.enums.SettingKeys

class SettingsLocalDataSources(private val settingsDAO: SettingsDAO): SettingDataSourceI {
    override suspend fun getSetting(key: SettingKeys): Setting {
        var value = settingsDAO.get(key.key)
        if (value == null) {
            value = Setting(key.key, key.defValue)
        }
        return value
    }

    override suspend  fun setSetting(setting: Setting) {
        settingsDAO.set(setting)
    }
}