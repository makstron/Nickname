package com.klim.nickname.app.window.generator

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klim.nickname.app.window.generator.entity_view.UserNameEntityView
import com.klim.nickname.domain.repositories.nickname.models.NicknameEntity
import com.klim.nickname.domain.settings.SettingsUseCase
import com.klim.nickname.domain.useCases.UsernameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeneratorViewModel
@Inject
constructor(private val nicknameUseCase: UsernameUseCase, private val settingUseCase: SettingsUseCase) : ViewModel() {

    private val _names = MutableLiveData<ArrayList<UserNameEntityView>>().apply { value = ArrayList() }
    val names: LiveData<ArrayList<UserNameEntityView>> = _names

    var username = ObservableField<UserNameEntityView>()

    private lateinit var nickname: NicknameEntity

    private var minLength = 0
    private var maxLength = 0

    fun loadSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            minLength = settingUseCase.getMinLength()
            maxLength = settingUseCase.getMaxLength()
        }
    }

    fun generateNewUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            if (maxLength == 0) {
                minLength = settingUseCase.getMinLength()
                maxLength = settingUseCase.getMaxLength()
            }
            nickname = nicknameUseCase.getNewUserName(minLength, maxLength, false)

            withContext(Dispatchers.Main) {
                val userName = UserNameEntityView(nickname.name)
                username.set(userName)

                names.value?.add(0, userName)
                _names.postValue(names.value)
            }
        }
    }

    fun save() {
        username.get()?.isSaved = true
        viewModelScope.launch(Dispatchers.IO) {
            username.get()?.let { nicknameUseCase.save(nickname) }
        }
    }

}