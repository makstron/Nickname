package com.klim.nickname.data.repositories.settings

import com.klim.nickname.data.db.tables.Setting
import com.klim.nickname.domain.settings.enums.SettingKeys

interface SettingDataSourceI {

    fun getSetting(key: SettingKeys): Setting

    fun setSetting(setting: Setting)
}