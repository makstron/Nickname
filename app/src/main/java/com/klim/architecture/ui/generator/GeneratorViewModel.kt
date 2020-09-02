package com.klim.architecture.ui.generator

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klim.architecture.domain.models.UserName
import com.klim.architecture.domain.useCases.UsernameUseCase
import com.klim.architecture.ui.entities.UserNameEntity
import com.klim.architecture.ui.entities.helpers.UserNameH

class GeneratorViewModel(val useCase: UsernameUseCase) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _names = MutableLiveData<ArrayList<UserNameEntity>>().apply {
        value = ArrayList<UserNameEntity>()
    }
    val names: LiveData<ArrayList<UserNameEntity>> = _names

    var username = ObservableField<UserNameEntity>()
    private var usernameTemp: UserNameEntity? = null

    fun generateNewUserName() {
        val userName = UserNameEntity(useCase.getNewUserName(4, 10, false).name, "")
        username.set(userName)

        usernameTemp?.let {
            names.value?.add(0, it)
            _names.postValue(names.value)
        }
        usernameTemp = username.get()?.let { UserNameH.clone(it) }
    }

    fun save() {
        username.get()?.let { useCase.save(UserName(it.name, System.currentTimeMillis())) }
    }

}