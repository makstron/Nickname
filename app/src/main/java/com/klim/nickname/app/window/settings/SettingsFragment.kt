package com.klim.nickname.app.window.settings

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.slider.RangeSlider
import com.klim.nickname.App
import com.klim.nickname.databinding.FragmentSettingsBinding
import com.klim.nickname.di.settings.SettingsModule
import javax.inject.Inject

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SettingsViewModel

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        (activity.application as App).appComponent.getSettingsComponent(SettingsModule()).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadSettings()

        viewModel.minMaxLength.observe(viewLifecycleOwner, { minMaxLength ->
            binding.tveLabelMinLengthValue.text = "${minMaxLength.first}"
            binding.tveLabelMaxLengthValue.text = "${minMaxLength.second}"
            binding.rsbMinMax.setValues(minMaxLength.first.toFloat(), minMaxLength.second.toFloat())
        })

        binding.rsbMinMax.addOnChangeListener(
            RangeSlider.OnChangeListener { slider, value, fromUser ->
                if (fromUser) {
                    val values = slider.values
                    binding.tveLabelMinLengthValue.text = "${values[0].toInt()}"
                    binding.tveLabelMaxLengthValue.text = "${values[1].toInt()}"
                }
            })

        binding.rsbMinMax.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {}

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = slider.values
                viewModel.setMinLength(values[0].toInt())
                viewModel.setMaxLength(values[1].toInt())
            }
        })

    }
}