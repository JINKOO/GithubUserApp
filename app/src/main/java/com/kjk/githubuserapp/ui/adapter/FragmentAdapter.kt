package com.kjk.githubuserapp.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kjk.githubuserapp.ui.localsearch.LocalSearchFragment
import com.kjk.githubuserapp.ui.remotesearch.RemoteSearchFragment

class FragmentAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> RemoteSearchFragment.newInstance()
            1 -> LocalSearchFragment.newInstance()
            else -> RemoteSearchFragment.newInstance()
        }
    }
}