package com.kjk.githubuserapp.ui.localsearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kjk.githubuserapp.R
import com.kjk.githubuserapp.databinding.FragmentLocalSearchBinding
import com.kjk.githubuserapp.ui.adapter.OnItemClickListener
import com.kjk.githubuserapp.ui.adapter.UserAdapter

class LocalSearchFragment : Fragment() {

    private lateinit var binding: FragmentLocalSearchBinding

    private lateinit var viewModel: LocalSearchViewModel

    private val userAdapter: UserAdapter by lazy {
        UserAdapter(OnItemClickListener { user, imageView ->
            Log.d(TAG, ": ${user.isFavorite}")
            viewModel.deleteFavoriteUser(user)
        })
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LocalSearchViewModel::class.java)

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
        viewModel.favoriteUsers.observe(viewLifecycleOwner) {
            Log.d(TAG, "observe: ${it.size}")
        }
    }

    companion object {
        private const val TAG = "LocalSearchFragment"

        fun newInstance() = LocalSearchFragment()
    }
}