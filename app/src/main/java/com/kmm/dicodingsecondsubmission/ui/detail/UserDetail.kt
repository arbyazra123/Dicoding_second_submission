package com.kmm.dicodingsecondsubmission.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.kmm.dicodingsecondsubmission.R
import com.kmm.dicodingsecondsubmission.data.model.UserItem

import com.kmm.dicodingsecondsubmission.databinding.FragmentUserDetailBinding
import com.kmm.dicodingsecondsubmission.utlity.DrawerInterface
import com.kmm.dicodingsecondsubmission.utlity.OnBackHandler
import com.kmm.dicodingsecondsubmission.utlity.StateHandler
class UserDetail : Fragment(), OnBackHandler,DrawerInterface {
    private lateinit var binding: FragmentUserDetailBinding
    private val userViewModel by viewModels<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(layoutInflater)
        val user = arguments?.getParcelable<UserItem>(DATA)

        user?.let {
            userViewModel.getUser(it.login)
        }

        userViewModel.userDetail.observe(this) { result ->
            when (result) {
                is StateHandler.Success -> {
                    binding.progressBar.visibility = ProgressBar.GONE
                    binding.coordinator.visibility = View.VISIBLE
                    result.data?.let { it ->
                        Glide.with(this).load(it.avatarUrl).into(binding.detailImg)
                        binding.followers.text =
                            getString(R.string.follower, it.followers.toString())
                        binding.followings.text =
                            getString(R.string.following, it.following.toString())
                        binding.appBarTitle.title = it.name ?: it.login
                        binding.location.text = it.location?.toString() ?: "no-location"
                        binding.workingAt.text = it.company ?: "no-company"
                        binding.repository.text = getString(R.string.repository, it.publicRepos.toString())
                        binding.viewPager.adapter = AdapterPager(this, it)
                        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                            tab.text = when (position) {
                                0 -> "Followers"
                                1 -> "Followings"
                                else -> "Undefined"
                            }

                        }.attach()
                    }


                }
                is StateHandler.Loading -> {
                    binding.progressBar.visibility = ProgressBar.VISIBLE
                    binding.coordinator.visibility = View.GONE
                }
                is StateHandler.Error -> {
                    binding.progressBar.visibility = ProgressBar.GONE
                    Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                }
            }

        }
        return binding.root
    }

    override fun onBackPressed(): Boolean {
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        return false
    }

    companion object {
        const val DATA = "data"
        const val USERNAME = "username"
    }

    override fun lockDrawer() {
//        binding.mainDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun unlockDrawer() {
//        binding.mainDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }
}