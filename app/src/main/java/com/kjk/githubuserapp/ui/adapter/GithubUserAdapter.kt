package com.kjk.githubuserapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kjk.githubuserapp.R
import com.kjk.githubuserapp.databinding.ListHeaderItemBinding
import com.kjk.githubuserapp.databinding.ListUserItemBinding
import com.kjk.githubuserapp.domain.GithubUserVO
import java.lang.ClassCastException

/**
 *  remote, local에서 사용자 검색 시 사용하는 AdapterClass
 *  DiffUtil을 사용한다.
 *  또한, alphabet index를 표시해야 하므로, header가 필요하다.
 *  DataItem이라는 Sealed Class를 사용해,
 *  viewType에 따라 경우에 따라, header를 포함한다.
 */
class GithubUserAdapter(
    private val callBackListener: OnItemClickListener
) : ListAdapter<DataItem, RecyclerView.ViewHolder>(GithubUserDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> {
                ItemIndexViewHolder.from(parent)
            }
            ITEM_VIEW_TYPE_ITEM -> {
                UserViewHolder.from(parent)
            }
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> {
                val githubUserVO = getItem(position) as DataItem.GithubUserItem
                holder.bind(githubUserVO.githubUserVO, callBackListener)
            }
            is ItemIndexViewHolder -> {
                val githubUserVO = getItem(position) as DataItem.Header
                holder.bind(githubUserVO.githubUserVO)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.GithubUserItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun addHeaderAndSubmitList(githubUserList: List<GithubUserVO>) {
        val items = mutableListOf<DataItem>()
        if (!githubUserList.isNullOrEmpty()) {
            items.add(DataItem.Header(githubUserList.first()))
            for (index in 1 until githubUserList.size) {
                val prevUserNameStart = githubUserList[index - 1].name
                val currUserNameStart = githubUserList[index].name

                items.add(DataItem.GithubUserItem(githubUserList[index - 1]))
                if (prevUserNameStart.first() != currUserNameStart.first()) {
                    items.add(DataItem.Header(githubUserList[index]))
                }
            }
            items.add(DataItem.GithubUserItem(githubUserList.last()))
        }
        submitList(items)
    }


    companion object {
        private const val TAG = "GithubUserAdapter"
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }


    class ItemIndexViewHolder(
        private val binding: ListHeaderItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(githubUserVO: GithubUserVO) {
            binding.apply {
                user = githubUserVO
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ItemIndexViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ListHeaderItemBinding>(
                    inflater,
                    R.layout.list_header_item,
                    parent,
                    false
                )
                return ItemIndexViewHolder(binding)
            }
        }
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

class GithubUserDiffUtilCallBack : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}


class OnItemClickListener(
    private val callBackListener: (githubUserVO: GithubUserVO, isChecked: ImageView) -> Unit
) {
    fun itemClickListener(githubUserVO: GithubUserVO, isChecked: ImageView) =
        callBackListener(githubUserVO, isChecked)
}


sealed class DataItem {
    abstract val id: Long

    data class GithubUserItem(val githubUserVO: GithubUserVO) : DataItem() {
        override val id = githubUserVO.idNumber
    }

    data class Header(val githubUserVO: GithubUserVO) : DataItem() {
        override val id = githubUserVO.idNumber
    }
}