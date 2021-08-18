package com.klim.nickname.app.window.generator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klim.nickname.app.window.generator.entity_view.UserNameEntityView
import com.klim.nickname.domain.repositories.nickname.models.NicknameEntity
import com.klim.nickname.domain.settings.SettingsUseCase
import com.klim.nickname.domain.useCases.NicknameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeneratorViewModel
@Inject
constructor(private val nicknameUseCase: NicknameUseCase, private val settingUseCase: SettingsUseCase) : ViewModel() {

    private val _names = MutableLiveData<ArrayList<UserNameEntityView>>().apply { value = ArrayList() }
    val names: LiveData<ArrayList<UserNameEntityView>> = _names

    private var lastUsername = ObservableField<UserNameEntityView>()

    private var minLength = 0
    private var maxLength = 0

    fun init() {
        viewModelScope.launch {
            loadSettings()
            val nickname: NicknameEntity = generateNewUserName()
            showGeneratedName(nickname)
        }
    }

    fun loadSettingsAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            loadSettings()
        }
    }

    private suspend fun loadSettings() {
        minLength = settingUseCase.getMinLength()
        maxLength = settingUseCase.getMaxLength()
    }

    fun generateNewUserNameAsync() {
        viewModelScope.launch {
            val nickname: NicknameEntity = generateNewUserName()
            showGeneratedName(nickname)
        }
    }

    private suspend fun generateNewUserName(): NicknameEntity = withContext(Dispatchers.IO) {
        nicknameUseCase.getNewUserName(minLength, maxLength, false)
    }

    private fun showGeneratedName(nickname: NicknameEntity) {
        val userName = UserNameEntityView(nickname)
        lastUsername.set(userName)

        names.value?.add(0, userName)
        _names.postValue(names.value)
    }

    fun save() {
        viewModelScope.launch {
            lastUsername.get()?.let { lastUsername ->
                lastUsername.isSaved = true
                lastUsername.nickname?.let {
                    nicknameUseCase.save(it)
                }
            }
        }
    }

    fun copyToClipboard(context: Context, nickname: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("nickname", nickname)
        clipboard!!.setPrimaryClip(clip)
    }

}