package com.klim.architecture.ui.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klim.architecture.domain.useCases.UsernameUseCase
import com.klim.architecture.ui.entities.UserNameEntity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SavedViewModel(private val useCase: UsernameUseCase) : ViewModel() {

    private val _names = MutableLiveData<ArrayList<UserNameEntity>>().apply {
//        value = "This is dashboard Fragment"
    }
    val names: LiveData<ArrayList<UserNameEntity>> = _names

    fun getAllSaved() {
        val useNameEntity = ArrayList<UserNameEntity>()

        var format = SimpleDateFormat("HH:mm:ss dd.MM.yyyy")

        useCase.getAllSaved().forEach {
            useNameEntity.add(UserNameEntity(it.name, format.format(Date(it.time))))
        }
        _names.value = useNameEntity
    }
}