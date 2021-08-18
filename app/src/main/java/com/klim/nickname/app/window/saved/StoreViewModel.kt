package com.klim.nickname.app.window.saved

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klim.nickname.app.window.saved.entity_view.UserNameStoredEntityView
import com.klim.nickname.domain.useCases.NicknameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class StoreViewModel
@Inject
constructor(private val useCase: NicknameUseCase) : ViewModel() {

    private val _names = MutableLiveData<ArrayList<UserNameStoredEntityView>>().apply { value = ArrayList() }
    val names: LiveData<ArrayList<UserNameStoredEntityView>> = _names

    fun loadAllSaved() {
        val useNameEntity = ArrayList<UserNameStoredEntityView>()
        val format = SimpleDateFormat("HH:mm:ss dd.MM.yyyy")

        viewModelScope.launch {
            //todo show progress
            withContext(Dispatchers.IO) {
                useCase.getAllSaved().forEach {
                    useNameEntity.add(UserNameStoredEntityView(it.name, format.format(Date(it.time))))
                }
            }
            _names.value = useNameEntity
            //todo stop progress
        }
    }

    fun copyToClipboard(context: Context, nickname: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("nickname", nickname)
        clipboard!!.setPrimaryClip(clip)
    }
}