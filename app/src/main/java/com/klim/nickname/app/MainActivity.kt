package com.klim.nickname.app

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.klim.nickname.R
import com.klim.nickname.app.window.generator.GeneratorFragment
import com.klim.nickname.app.window.saved.StoreFragment
import com.klim.nickname.app.window.settings.SettingsFragment
import com.klim.nickname.databinding.ActivityMainBinding
import java.lang.ref.WeakReference
import kotlin.collections.set

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel
    private lateinit var binding: ActivityMainBinding

    private val fragmentsMap = HashMap<String, WeakReference<Fragment>>()
    private var currentFragment: WeakReference<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = vm

        customizeView()
        openStartFragment()
        setActions()
    }

    private fun customizeView() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        window.statusBarColor = Color.parseColor("#50FFFFFF")
        window.navigationBarColor = Color.GRAY
        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun openStartFragment() {
        //start fragment
        val startFragment = GeneratorFragment()
        currentFragment = WeakReference(startFragment)
        fragmentsMap[startFragment::class.java.simpleName] = WeakReference(startFragment)
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, startFragment).commit()
    }

    private fun setActions() {
        binding.navView.setOnNavigationItemSelectedListener { item ->
            val key = when (item.itemId) {
                R.id.navigation_generator -> GeneratorFragment::class.java.simpleName
                R.id.navigation_store -> StoreFragment::class.java.simpleName
                R.id.navigation_settings -> SettingsFragment::class.java.simpleName
                else -> GeneratorFragment::class.java.simpleName
            }

            currentFragment?.get()?.let {
                supportFragmentManager.beginTransaction().hide(it).commit()
            }

            val fragment = fragmentsMap[key]?.get()?.apply {
                supportFragmentManager.beginTransaction().show(this).commit()
            } ?: run {
                when (item.itemId) {
                    R.id.navigation_generator -> GeneratorFragment()
                    R.id.navigation_store -> StoreFragment()
                    R.id.navigation_settings -> SettingsFragment()
                    else -> GeneratorFragment()
                }.apply {
                    fragmentsMap[key] = WeakReference(this)
                    supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, this).commit()
                }
            }

            currentFragment = WeakReference(fragment)

            true
        }
    }

}