package com.klim.nickname.app.window.saved

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.klim.nickname.App
import com.klim.nickname.R
import com.klim.nickname.databinding.FragmentStoreBinding
import com.klim.nickname.di.saved.SavedModule
import com.klim.nickname.di.saved.SavedScope
import javax.inject.Inject

class StoreFragment
@SavedScope
constructor() : Fragment() {

    private lateinit var binding: FragmentStoreBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: StoreViewModel

    private val nicknamesAdapter = StoredNicknamesAdapter()

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        (activity.application as App).appComponent.getSavedComponent(SavedModule()).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StoreViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)
        binding.vm = viewModel

        settingView()

        nicknamesAdapter.itemClick = itemClickListener

        viewModel.names.observe(viewLifecycleOwner, Observer {newNicknames ->
            nicknamesAdapter.names.clear()
            nicknamesAdapter.names.addAll(newNicknames)
            nicknamesAdapter.notifyDataSetChanged()
            binding.tvPlaceholder.visibility = if (viewModel.names.value?.size ?: 0 > 0) View.GONE else View.VISIBLE
        })

        viewModel.loadAllSaved()

        return binding.root
    }

    private fun settingView() {
        binding.rvList.layoutManager = LinearLayoutManager(activity)
        binding.rvList.adapter = nicknamesAdapter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            viewModel.loadAllSaved()
        }
    }

    private val itemClickListener: (String) -> Unit = { nickname ->
        viewModel.copyToClipboard(requireContext(), nickname)
        Toast.makeText(requireActivity(), getString(R.string.nickname_was_copied, nickname), Toast.LENGTH_SHORT).show()
    }
}