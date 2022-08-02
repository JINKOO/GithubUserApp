package com.kjk.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kjk.githubuserapp.R
import com.kjk.githubuserapp.databinding.ListUserItemBinding
import com.kjk.githubuserapp.domain.GithubUserVO

class UserAdapter(
    private val callBackListener: OnItemClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var githubUserList: List<GithubUserVO> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(githubUserList[position], callBackListener)
    }

    override fun getItemCount() = githubUserList.size

    fun updateList(githubUserList: List<GithubUserVO>) {
        this.githubUserList = githubUserList
        notifyDataSetChanged()
    }


    class UserViewHolder(
        private val binding: ListUserItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(githubUserVO: GithubUserVO, callBackListener: OnItemClickListener) {
            binding.apply {
                user = githubUserVO
                clickListener = callBackListener
                imageview = favoriteCheckbox
                executePendingBindings()
            }
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


class OnItemClickListener(
    private val callBackListener: (githubUserVO: GithubUserVO, isChecked: ImageView) -> Unit
) {
    fun itemClickListener(githubUserVO: GithubUserVO, isChecked: ImageView) = callBackListener(githubUserVO, isChecked)
}