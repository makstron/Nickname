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

    private lateinit var nicknamesAdapter: GeneratorNicknamesAdapter

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
            binding.rvLatest.adapter?.notifyDataSetChanged()
            binding.rvLatest.scrollToPosition(0)
        })

        nicknamesAdapter = GeneratorNicknamesAdapter()
        nicknamesAdapter.itemClick = itemClickListener
        val lm = LinearLayoutManager(activity, RecyclerView.VERTICAL, true)
        binding.rvLatest.layoutManager = lm
        binding.rvLatest.adapter = nicknamesAdapter

        val helper: SnapHelper = GravitySnapHelper(Gravity.TOP)
        helper.attachToRecyclerView(binding.rvLatest)

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

    private val itemClickListener: (String) -> Unit = { nickname ->
        val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("nickname", nickname)
        clipboard!!.setPrimaryClip(clip)
        Toast.makeText(requireActivity(), getString(R.string.nickname_was_copied, nickname) , Toast.LENGTH_SHORT).show()
    }

    private lateinit var avd: AnimatedVectorDrawable

    private fun setAction() {
        binding.acivStar.setOnClickListener {
            val avd = binding.acivStar.drawable as AnimatedVectorDrawable
            avd.stop()
            avd.start()

            viewModel.save()
            binding.rvLatest.adapter?.notifyItemChanged(0)
            binding.rvLatest.scrollToPosition(0)
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

    inner class GeneratorNicknamesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var itemClick: ((String) -> Unit)? = null

        private val itemSelected = View.OnClickListener {view ->
            itemClick?.invoke(view.tag as String)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val vh = UserNameViewHolder(ItemLatestUsernameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            vh.itemView.setOnClickListener(itemSelected)
            return vh
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as UserNameViewHolder
            val userName: UserNameEntityView? = viewModel.names.value?.get(position)

            userName?.let { nickname ->
                viewHolder.itemView.tag = nickname.name
                viewHolder.binding.tvUserName.text = nickname.name
                if (nickname.isSaved) {
                    viewHolder.binding.ivStarLeft.visibility = View.VISIBLE
                    viewHolder.binding.ivStarRight.visibility = View.VISIBLE
                } else {
                    viewHolder.binding.ivStarLeft.visibility = View.GONE
                    viewHolder.binding.ivStarRight.visibility = View.GONE
                }
            }
        }

        override fun getItemCount(): Int {
            return viewModel.names.value?.size ?: 0
        }


    }
}

class UserNameViewHolder(var binding: ItemLatestUsernameBinding) : RecyclerView.ViewHolder(binding.root)