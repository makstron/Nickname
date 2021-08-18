package com.klim.nickname.app.window.saved

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.recyclerview.widget.RecyclerView
import com.klim.nickname.App
import com.klim.nickname.R
import com.klim.nickname.databinding.FragmentStoreBinding
import com.klim.nickname.databinding.ItemSavedUsernameBinding
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

    private lateinit var nicknamesAdapter: StoreNicknamesAdapter

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        (activity.application as App).appComponent.getSavedComponent(SavedModule()).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StoreViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)
        binding.vm = viewModel


        var lm = LinearLayoutManager(activity)
        nicknamesAdapter = StoreNicknamesAdapter()
        nicknamesAdapter.itemClick = itemClickListener
        binding.rvList.layoutManager = lm
        binding.rvList.adapter = nicknamesAdapter

        viewModel.names.observe(viewLifecycleOwner, Observer {
            (binding.rvList.adapter as StoreNicknamesAdapter).notifyDataSetChanged()
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

    private val itemClickListener: (String) -> Unit = { nickname ->
        val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("nickname", nickname)
        clipboard!!.setPrimaryClip(clip)
        Toast.makeText(requireActivity(), getString(R.string.nickname_was_copied, nickname) , Toast.LENGTH_SHORT).show()
    }

    inner class StoreNicknamesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var itemClick: ((String) -> Unit)? = null

        private val itemSelected = View.OnClickListener {view ->
            itemClick?.invoke(view.tag as String)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val vh = UserNameViewHolder(ItemSavedUsernameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            vh.itemView.setOnClickListener(itemSelected)
            return vh
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as UserNameViewHolder
            val userName = viewModel.names.value?.get(position)

            userName?.let {nickname ->
                viewHolder.itemView.tag = nickname.name
                viewHolder.binding.tvUserName.text = nickname.name
                viewHolder.binding.tvTime.text = nickname.dateTime
                viewHolder.binding.executePendingBindings()
            }
        }

        override fun getItemCount(): Int {
            return viewModel.names.value?.size ?: 0
        }

    }
}

class UserNameViewHolder(val binding: ItemSavedUsernameBinding) : RecyclerView.ViewHolder(binding.root)