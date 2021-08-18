package com.klim.nickname.app.window.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klim.nickname.domain.settings.SettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsViewModel
@Inject
constructor(private val useCase: SettingsUseCase) : ViewModel() {

    private val _minMaxLength = MutableLiveData<Pair<Int, Int>>()
    val minMaxLength: LiveData<Pair<Int, Int>> = _minMaxLength

    fun setMinLength(minLength: Int) {
        viewModelScope.launch {
            useCase.setMinLength(minLength)
        }
    }

    fun setMaxLength(maxLength: Int) {
        viewModelScope.launch {
            useCase.setMaxLength(maxLength)
        }
    }

    fun loadSettings() {
        viewModelScope.launch {
            //show progress
            _minMaxLength.postValue(
                Pair(
                    useCase.getMinLength(),
                    useCase.getMaxLength()
                )
            )
            //hide progress
        }
    }
}