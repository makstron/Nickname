package com.klim.nickname.app.window.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klim.nickname.app.window.saved.entity_view.UserNameStoredEntityView
import com.klim.nickname.domain.useCases.UsernameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class StoreViewModel
@Inject
constructor(private val useCase: UsernameUseCase) : ViewModel() {

    private val _names = MutableLiveData<ArrayList<UserNameStoredEntityView>>().apply { value = ArrayList() }
    val names: LiveData<ArrayList<UserNameStoredEntityView>> = _names

    fun getAllSaved() {
        val useNameEntity = ArrayList<UserNameStoredEntityView>()
        val format = SimpleDateFormat("HH:mm:ss dd.MM.yyyy")

        viewModelScope.launch(Dispatchers.Main) {
            //show progress
            withContext(Dispatchers.IO) {
                useCase.getAllSaved().forEach {
                    useNameEntity.add(UserNameStoredEntityView(it.name, format.format(Date(it.time))))
                }
            }
            _names.value = useNameEntity
            //stop progress
        }
    }
}