package com.klim.nickname.app

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.klim.nickname.app.window.generator.GeneratorFragment
import java.lang.ref.SoftReference

class MainViewModel : ViewModel() {

    val fragmentsMap = HashMap<String, SoftReference<Fragment>>()
    var currentFragment: SoftReference<Fragment>? = null

    fun openStartFragment(transactionCallback: (Fragment) -> Unit) {
        //start fragment
        var needAdd = false
        var startFragment = fragmentsMap[GeneratorFragment::class.java.simpleName]?.get()
        if (startFragment == null) {
            startFragment = GeneratorFragment()
            needAdd = true
            fragmentsMap[GeneratorFragment::class.java.simpleName] = SoftReference(startFragment)
        }
        currentFragment = fragmentsMap[GeneratorFragment::class.java.simpleName]

        if (needAdd) {
            transactionCallback(startFragment)
        }
    }

}