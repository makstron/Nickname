package com.klim.nickname.app.window.generator

import android.R.attr.label
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.klim.nickname.App
import com.klim.nickname.R
import com.klim.nickname.app.window.generator.entity_view.UserNameEntityView
import com.klim.nickname.databinding.FragmentGeneratorBinding
import com.klim.nickname.databinding.ItemLatestUsernameBinding
import com.klim.nickname.di.generator.GeneratorModule
import javax.inject.Inject


class GeneratorFragment : Fragment() {

    private lateinit var binding: FragmentGeneratorBinding
    private lateinit var viewModel: GeneratorViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var nicknamesAdapter = NicknamesAdapter()

    private var enableGenerate = true //prevent click until animation end

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        (activity.application as App).appComponent.getGeneratorComponent(GeneratorModule()).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GeneratorViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_generator, container, false)
        binding.vm = viewModel

        viewModel.names.observe(viewLifecycleOwner, Observer {
            nicknamesAdapter.nicknames.clear()
            nicknamesAdapter.nicknames.addAll(it)

            binding.rvLatest.adapter?.notifyDataSetChanged()
            binding.rvLatest.scrollToPosition(0)
        })

        settingView()
        setAction()

        viewModel.init()

        return binding.root
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            viewModel.loadSettingsAsync()
        }
    }

    private val itemClickListener: (String) -> Unit = { nickname ->
        viewModel.copyToClipboard(requireContext(), nickname)

        Toast.makeText(requireActivity(), getString(R.string.nickname_was_copied, nickname), Toast.LENGTH_SHORT).show()
    }

    private fun settingView() {
        val lm = LinearLayoutManager(activity, RecyclerView.VERTICAL, true)
        binding.rvLatest.layoutManager = lm
        binding.rvLatest.adapter = nicknamesAdapter

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (binding.acivRefresh.drawable as AnimatedVectorDrawable)
                .registerAnimationCallback(object : Animatable2.AnimationCallback() {
                    override fun onAnimationStart(drawable: Drawable?) {
                        super.onAnimationStart(drawable)
                        enableGenerate = false
                    }

                    override fun onAnimationEnd(drawable: Drawable?) {
                        super.onAnimationEnd(drawable)
                        viewModel.generateNewUserNameAsync()
                        enableGenerate = true
                    }
                })
        }
    }

    private fun setAction() {
        nicknamesAdapter.itemClick = itemClickListener

        binding.starIcon.setOnClickListener {
            setStar()
        }

        binding.acivRefresh.setOnClickListener {
            generateNickname()
        }
    }

    private fun setStar() {
        val avd = binding.starIcon.drawable as AnimatedVectorDrawable
        avd.stop()
        avd.start()

        viewModel.save()
        binding.rvLatest.adapter?.notifyItemChanged(0)
        binding.rvLatest.scrollToPosition(0)
    }

    private fun generateNickname() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            viewModel.generateNewUserNameAsync()
        } else if (enableGenerate) {
            (binding.acivRefresh.drawable as AnimatedVectorDrawable).start()
        }
    }
}