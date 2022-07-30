package com.kjk.githubuserapp.ui.localsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kjk.githubuserapp.R
import com.kjk.githubuserapp.databinding.FragmentLocalSearchBinding

class LocalSearchFragment : Fragment() {

    private lateinit var binding: FragmentLocalSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_local_search,
            container,
            false
        )
        return binding.root
    }
}