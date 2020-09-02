package com.klim.architecture.ui.saved

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
import com.klim.architecture.App
import com.klim.architecture.R
import com.klim.architecture.data.mappers.UserNameUserNameEntityMapper
import com.klim.architecture.data.repositories.userName.UserNameRepository
import com.klim.architecture.data.repositories.userName.dataSources.LocalDataSource
import com.klim.architecture.databinding.FragmentSavedBinding
import com.klim.architecture.domain.useCases.UsernameUseCase

class DashboardFragment : Fragment() {

    private lateinit var viewModel: SavedViewModel
    private lateinit var binding: FragmentSavedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(UsernameUseCase(UserNameRepository(LocalDataSource((activity?.application as App).db), UserNameUserNameEntityMapper()))))
            .get(SavedViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved, container, false)
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

    inner class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return UserNameViewHolder(parent)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as UserNameViewHolder
            var userName = viewModel.names.value?.get(position)

            viewHolder.tvUserName.setText(userName?.name)
            viewHolder.tvTime.setText("" + userName?.time)
        }

        override fun getItemCount(): Int {
            return viewModel.names.value?.size ?: 0
        }

    }

    class UserNameViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_saved_username, parent, false)) {

        var tvUserName: TextView
        var tvTime: TextView

        init {
            tvUserName = itemView.findViewById(R.id.tvUserName)
            tvTime = itemView.findViewById(R.id.tvTime)
        }
    }

}


class MyViewModelFactory(private val mParam: UsernameUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SavedViewModel(mParam) as T
    }
}