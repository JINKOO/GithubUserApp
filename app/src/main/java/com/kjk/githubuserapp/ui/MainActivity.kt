package com.kjk.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.kjk.githubuserapp.R
import com.kjk.githubuserapp.databinding.ActivityMainBinding
import com.kjk.githubuserapp.ui.adapter.FragmentAdapter

/**
 *  ViewPager2, Tablayout을 관리하는 Activity이다.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fragmentAdapter: FragmentAdapter by lazy {
        FragmentAdapter(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initLayout()
    }

    private fun initLayout() {
        initViewPager()
        initTabLayout()
    }

    private fun initViewPager() {
        binding.viewPager2.apply {
            adapter = fragmentAdapter
        }
    }

    private fun initTabLayout() {
        binding.apply {
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = getString(R.string.tab_api_title)
                    }
                    1 -> {
                        tab.text = getString(R.string.tab_local_title)
                    }
                }
            }.attach()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}