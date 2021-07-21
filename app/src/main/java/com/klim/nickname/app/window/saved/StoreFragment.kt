package com.klim.nickname.app.window.saved

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.klim.nickname.App
import com.klim.nickname.R
import com.klim.nickname.databinding.FragmentStoreBinding
import com.klim.nickname.databinding.ItemSavedUsernameBinding
import com.klim.nickname.di.saved.SavedModule
import com.klim.nickname.di.saved.SavedScope
import com.klim.nickname.domain.useCases.UsernameUseCase
import javax.inject.Inject

class StoreFragment
@SavedScope
constructor() : Fragment() {

    private lateinit var binding: FragmentStoreBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: StoreViewModel

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        (activity.application as App).appComponent.getSavedComponent(SavedModule()).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StoreViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)
        binding.vm = viewModel


        var lm = LinearLayoutManager(activity)
//        lm.setReverseLayout(true)
        binding.rvList.layoutManager = lm
        binding.rvList.adapter = Adapter()

        viewModel.names.observe(viewLifecycleOwner, Observer {
            (binding.rvList.adapter as Adapter).notifyDataSetChanged()
            binding.tvPlaceholder.visibility = if (viewModel.names.value?.size ?: 0 > 0) View.GONE else View.VISIBLE
        })

        viewModel.getAllSaved()

        return binding.root
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            viewModel.getAllSaved()
        }
    }

    inner class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return UserNameViewHolder(ItemSavedUsernameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as UserNameViewHolder
            val userName = viewModel.names.value?.get(position)

            viewHolder.binding.tvUserName.text = userName?.name
            viewHolder.binding.tvTime.text = "${userName?.dateTime}"
        }

        override fun getItemCount(): Int {
            return viewModel.names.value?.size ?: 0
        }

    }

    class UserNameViewHolder(val binding: ItemSavedUsernameBinding) : RecyclerView.ViewHolder(binding.root)

}