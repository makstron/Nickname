package com.klim.nickname.ui.generator

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
import com.klim.nickname.data.mappers.UserNameUserNameEntityMapper
import com.klim.nickname.data.repositories.userName.UserNameRepository
import com.klim.nickname.data.repositories.userName.dataSources.LocalDataSource
import com.klim.nickname.databinding.FragmentGeneratorBinding
import com.klim.nickname.di.generator.GeneratorModule
import com.klim.nickname.domain.useCases.UsernameUseCase
import javax.inject.Inject


class GeneratorFragment : Fragment() {

    private lateinit var binding: FragmentGeneratorBinding
    private lateinit var viewModel: GeneratorViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity?.application as App).appComponent.getGeneratorComponent(GeneratorModule()).inject(this)

//        viewModel = ViewModelProviders.of(this, MyViewModelFactory(UsernameUseCase(UserNameRepository(LocalDataSource((activity?.application as App).db), UserNameUserNameEntityMapper())))).get(GeneratorViewModel::class.java)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GeneratorViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_generator, container, false)
        binding.vm = viewModel


//        val root = inflater.inflate(R.layout.fragment_generator, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
        viewModel.names.observe(viewLifecycleOwner, Observer {
            binding.rvLatest.adapter?.notifyDataSetChanged()
                binding.rvLatest.scrollToPosition(0)
        })

        var lm = LinearLayoutManager(activity)
        lm.setReverseLayout(true)
        binding.rvLatest.layoutManager = lm
        binding.rvLatest.adapter = Adapter()

        viewModel.generateNewUserName()

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
        }

        override fun getItemCount(): Int {
            return viewModel.names.value?.size ?: 0
        }

    }

    class UserNameViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_latest_username, parent, false)) {

        var tvUserName: TextView

        init {
            tvUserName = itemView.findViewById(R.id.tvUserName)
        }
    }
}


class MyViewModelFactory(mParam: Any) : ViewModelProvider.Factory {
    private val _mParam: Any
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GeneratorViewModel(_mParam as UsernameUseCase) as T
    }

    init {
        _mParam = mParam
    }
}