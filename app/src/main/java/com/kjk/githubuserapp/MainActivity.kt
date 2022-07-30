package com.kjk.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayout
import com.kjk.githubuserapp.databinding.ActivityMainBinding
import com.kjk.githubuserapp.ui.localsearch.LocalSearchFragmentDirections
import com.kjk.githubuserapp.ui.remotesearch.RemoteSearchFragment
import com.kjk.githubuserapp.ui.remotesearch.RemoteSearchFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.tabLayout.apply {
            addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab!!.position) {
                        0 -> {
                            moveToRemoteSearchFragment()
                        }
                        1 -> {
                            moveToLocalSearchFragment()
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }

    private fun moveToRemoteSearchFragment() {
        Log.d(TAG, "moveToRemoteSearchFragment: ")
        this.findNavController(R.id.nav_host_fragment)
            .navigate(LocalSearchFragmentDirections.actionLocalSearchFragmentToRemoteSearchFragment())
    }

    private fun moveToLocalSearchFragment() {
        this.findNavController(R.id.nav_host_fragment)
            .navigate(RemoteSearchFragmentDirections.actionRemoteSearchFragmentToLocalSearchFragment())
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}