package com.klim.nickname.app.window.generator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.klim.nickname.app.window.generator.entity_view.UserNameEntityView
import com.klim.nickname.databinding.ItemLatestUsernameBinding

class NicknamesAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val nicknames = ArrayList<UserNameEntityView>()
    var itemClick: ((String) -> Unit)? = null

    private val itemSelected = View.OnClickListener { view ->
        itemClick?.invoke(view.tag as String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh = NicknameViewHolder(ItemLatestUsernameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        vh.itemView.setOnClickListener(itemSelected)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as NicknameViewHolder
        val userName: UserNameEntityView? = nicknames[position]

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
            viewHolder.binding.executePendingBindings()
        }
    }

    override fun getItemCount() = nicknames.size
}