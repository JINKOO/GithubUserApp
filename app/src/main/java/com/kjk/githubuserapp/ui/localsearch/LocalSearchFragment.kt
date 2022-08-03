package com.kjk.githubuserapp.ui.localsearch

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kjk.githubuserapp.R
import com.kjk.githubuserapp.databinding.FragmentLocalSearchBinding
import com.kjk.githubuserapp.ui.adapter.GithubUserAdapter
import com.kjk.githubuserapp.ui.adapter.OnItemClickListener

/**
 *  Room DB를 사용해, 즐겨찾기를 추가한 사용자를 검색하는 UI Controller.
 */
class LocalSearchFragment : Fragment() {

    private lateinit var binding: FragmentLocalSearchBinding

    private lateinit var viewModel: LocalSearchViewModel

    private val userAdapter: GithubUserAdapter by lazy {
        GithubUserAdapter(OnItemClickListener { user, imageView ->
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
        setRecyclerView()
        setSearchKeyword()
    }

    private fun observe() {
        viewModel.showMessageEvent.observe(viewLifecycleOwner) { toShow ->
            if (toShow) {
                showToastMessage()
                viewModel.showMessageEventDone()
            }
        }

        viewModel.hideKeyboardEvent.observe(viewLifecycleOwner) { toHide ->
            if (toHide) {
                hideKeyboard()
                viewModel.hideKeyboardDone()
            }
        }
        
        viewModel.searchKeyword.observe(viewLifecycleOwner) { keyword ->
            if (keyword.isNullOrEmpty()) {
                viewModel.loadFavoriteUsersFromLocal()
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
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.setSearchKeyword(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
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


    private fun hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.keywordEdittext.windowToken, 0)
    }

    companion object {
        private const val TAG = "LocalSearchFragment"

        fun newInstance() = LocalSearchFragment()
    }
}