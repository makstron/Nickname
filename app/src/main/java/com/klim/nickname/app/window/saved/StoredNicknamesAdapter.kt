package com.klim.nickname.app.window.saved

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.klim.nickname.app.window.saved.entity_view.UserNameStoredEntityView
import com.klim.nickname.databinding.ItemSavedUsernameBinding

class StoredNicknamesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val names = ArrayList<UserNameStoredEntityView>()

    var itemClick: ((String) -> Unit)? = null

    private val itemSelected = View.OnClickListener { view ->
        itemClick?.invoke(view.tag as String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh = UserNameViewHolder(ItemSavedUsernameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        vh.itemView.setOnClickListener(itemSelected)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as UserNameViewHolder
        val nickname = names[position]

        viewHolder.itemView.tag = nickname.name
        viewHolder.binding.tvUserName.text = nickname.name
        viewHolder.binding.tvTime.text = nickname.dateTime
        viewHolder.binding.executePendingBindings()
    }

    override fun getItemCount() = names.size

}