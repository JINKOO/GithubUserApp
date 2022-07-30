package com.kjk.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kjk.githubuserapp.R
import com.kjk.githubuserapp.databinding.ListUserItemBinding
import com.kjk.githubuserapp.domain.UserEntity

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList: List<UserEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateList(userList: List<UserEntity>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    class UserViewHolder(
        private val binding: ListUserItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserEntity) {

        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ListUserItemBinding>(
                    inflater,
                    R.layout.list_user_item,
                    parent,
                    false
                )
                return UserViewHolder(binding)
            }
        }
    }
}