package com.klim.nickname.domain.settings.enums

enum class SettingKeys(val key: String, val defValue: String) {
    MIN_LENGTH("min_length", "5"),
    MAX_LENGTH("max_length", "8"),
    ;
}