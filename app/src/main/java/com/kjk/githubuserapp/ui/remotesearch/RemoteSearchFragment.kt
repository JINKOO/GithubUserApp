package com.kjk.githubuserapp.ui.remotesearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.kjk.githubuserapp.R
import com.kjk.githubuserapp.databinding.FragmentRemoteSearchBinding
import com.kjk.githubuserapp.ui.adapter.UserAdapter

class RemoteSearchFragment : Fragment() {

    private lateinit var binding: FragmentRemoteSearchBinding

    private lateinit var viewModel: RemoteSearchViewModel

    private val userAdapter: UserAdapter by lazy {
        UserAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_remote_search,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(RemoteSearchViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initLayout()
        observe()
    }

    private fun initLayout() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = userAdapter
        }
    }

    private fun observe() {
        viewModel.users.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observe: ${it.size}")
        })
    }

    companion object {
        private const val TAG = "RemoteSearchFragment"
    }
}