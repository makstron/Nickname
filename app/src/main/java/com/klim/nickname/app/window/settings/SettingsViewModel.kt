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

    val _minLength = MutableLiveData<Int>()
    val minLength: LiveData<Int> = _minLength

    val _maxLength = MutableLiveData<Int>()
    val maxLength: LiveData<Int> = _maxLength

    val _minMaxLength = MutableLiveData<Pair<Int, Int>>()
    val minMaxLength: LiveData<Pair<Int, Int>> = _minMaxLength

    fun setMinLength(minLength: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                useCase.setMinLength(minLength)
            }
        }
    }
    fun setMaxLength(maxLength: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                useCase.setMaxLength(maxLength)
            }
        }
    }

    fun loadSettings() {
        viewModelScope.launch(Dispatchers.Main) {
            //show progress
            withContext(Dispatchers.IO) {
                _minLength.postValue(useCase.getMinLength())
                _maxLength.postValue(useCase.getMaxLength())

                _minMaxLength.postValue(Pair(useCase.getMinLength(), useCase.getMaxLength()))
            }
            //hide progress
        }
    }
}