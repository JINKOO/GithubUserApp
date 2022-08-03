package com.kjk.githubuserapp.ui.remotesearch

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.kjk.githubuserapp.databinding.FragmentRemoteSearchBinding
import com.kjk.githubuserapp.ui.adapter.GithubUserAdapter
import com.kjk.githubuserapp.ui.adapter.OnItemClickListener


/**
 *  remote network로 부터 전달받은 data를 바탕으로
 *  검색해 사용자에게 보여주는 UI Controller
 */
class RemoteSearchFragment : Fragment() {

    private lateinit var binding: FragmentRemoteSearchBinding
    private lateinit var viewModel: RemoteSearchViewModel


    private val userAdapter: GithubUserAdapter by lazy {
        GithubUserAdapter(OnItemClickListener { user, favoriteImageView ->
            if (user.isFavorite) {
                // 즐겨찾기 해제
                user.isFavorite = false
                favoriteImageView.apply {
                    isSelected = false
                    setColorFilter(R.color.yellow)
                }
                viewModel.deleteFavoriteUser(user)
            } else {
                // 즐겨찾기 추가
                user.isFavorite = true
                favoriteImageView.apply {
                    isSelected = true
                    setColorFilter(R.color.white)
                }
                viewModel.addFavoriteUser(user)
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
        private const val TAG = "RemoteSearchFragment"

        fun newInstance() = RemoteSearchFragment()
    }
}