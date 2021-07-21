package com.klim.nickname.app.window.generator

import android.app.Activity
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.klim.nickname.App
import com.klim.nickname.R
import com.klim.nickname.databinding.FragmentGeneratorBinding
import com.klim.nickname.databinding.ItemLatestUsernameBinding
import com.klim.nickname.di.generator.GeneratorModule
import javax.inject.Inject

class GeneratorFragment : Fragment() {

    private lateinit var binding: FragmentGeneratorBinding
    private lateinit var viewModel: GeneratorViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        (activity.application as App).appComponent.getGeneratorComponent(GeneratorModule()).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GeneratorViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_generator, container, false)
        binding.vm = viewModel

        viewModel.names.observe(viewLifecycleOwner, Observer {
            binding.rvLatest.adapter?.notifyDataSetChanged()
            binding.rvLatest.scrollToPosition(0)
        })

        val lm = LinearLayoutManager(activity)
        lm.reverseLayout = true
        binding.rvLatest.layoutManager = lm
        binding.rvLatest.adapter = Adapter()

        viewModel.loadSettings()
        viewModel.generateNewUserName()

        setAction()

        return binding.root
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            viewModel.loadSettings()
        }
    }

    private lateinit var avd: AnimatedVectorDrawable

    private fun setAction() {
        binding.acivStar.setOnClickListener {
            val avd = binding.acivStar.drawable as AnimatedVectorDrawable
            avd.stop()
            avd.start()

            viewModel.save()
        }

        avd = binding.acivRefresh.drawable as AnimatedVectorDrawable

        binding.acivRefresh.setOnClickListener {

            if (enableGenerate) {
                avd.start()
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                viewModel.generateNewUserName()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            avd.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                override fun onAnimationStart(drawable: Drawable?) {
                    super.onAnimationStart(drawable)
                    enableGenerate = false
                }

                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)
                    viewModel.generateNewUserName()
                    enableGenerate = true
                }
            })
        }
    }

    private var enableGenerate = true

    inner class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return UserNameViewHolder(ItemLatestUsernameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as UserNameViewHolder
            val userName = viewModel.names.value?.get(position)

            viewHolder.binding.tvUserName.text = userName?.name
        }

        override fun getItemCount(): Int {
            return viewModel.names.value?.size ?: 0
        }
    }

    class UserNameViewHolder(var binding: ItemLatestUsernameBinding) : RecyclerView.ViewHolder(binding.root)
}