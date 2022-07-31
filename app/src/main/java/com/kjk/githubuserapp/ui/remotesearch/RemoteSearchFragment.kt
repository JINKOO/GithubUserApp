package com.kjk.githubuserapp.ui.remotesearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kjk.githubuserapp.R
import com.kjk.githubuserapp.databinding.FragmentRemoteSearchBinding
import com.kjk.githubuserapp.ui.adapter.OnItemClickListener
import com.kjk.githubuserapp.ui.adapter.UserAdapter

class RemoteSearchFragment : Fragment() {

    private lateinit var binding: FragmentRemoteSearchBinding

    private lateinit var viewModel: RemoteSearchViewModel

    private val userAdapter: UserAdapter by lazy {
        UserAdapter(OnItemClickListener {
            Log.d(TAG, "clickListener : ${it.name}, ${it.isFavorite}")
            if (it.isFavorite) {
                // 즐겨찾기 해제
                viewModel.deleteFavoriteUser(it)
            } else {
                // 즐겨찾기 추가
                viewModel.addFavoriteUser(it)
            }
        })
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
        setSearchKeyword()
        setRecyclerView()
    }


    private fun observe() {
        viewModel.users.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observe: ${it.size}")
        })

        viewModel.showMessageEvent.observe(viewLifecycleOwner) { toShow ->
            if (toShow) {
                showToastMessage()
                viewModel.showMessageEventDone()
            }
        }
    }


    private fun setRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = userAdapter
        }
    }


    private fun setSearchKeyword() {
        binding.keywordEdittext.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d(TAG, "onTextChanged: ${s.toString()}")
                    viewModel.setSearchKeyword(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }


    private fun showToastMessage() {
        Toast.makeText(
            requireActivity(),
            getString(R.string.search_leyword_empty_toast_message),
            Toast.LENGTH_SHORT
        ).show()
    }


    companion object {
        private const val TAG = "RemoteSearchFragment"
    }
}