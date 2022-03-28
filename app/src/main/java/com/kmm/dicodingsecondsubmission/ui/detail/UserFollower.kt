package com.kmm.dicodingsecondsubmission.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmm.dicodingsecondsubmission.R
import com.kmm.dicodingsecondsubmission.databinding.FragmentUserFollowerBinding
import com.kmm.dicodingsecondsubmission.ui.home.GithubUserAdapter
import com.kmm.dicodingsecondsubmission.utlity.StateHandler

class UserFollower : Fragment() {
    private lateinit var binding: FragmentUserFollowerBinding
    private lateinit var mAdapter: GithubUserAdapter
    private val userViewModel by viewModels<UserViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserFollowerBinding.inflate(layoutInflater)
        val url = arguments?.getString(UserDetail.USERNAME)
        url?.let {
            userViewModel.getUserFollowers(it)
        }
        mAdapter = GithubUserAdapter(context!!)
        binding.followersRv.layoutManager = LinearLayoutManager(context)
        binding.followersRv.adapter = mAdapter

        userViewModel.userFollowers.observe(this) { response ->
            when (response) {
                is StateHandler.Loading -> {
                    binding.followersRv.visibility = View.GONE
                    binding.followersRv.adapter = mAdapter
                    mAdapter.clearUsers()
                    binding.progressBar.visibility = ProgressBar.VISIBLE

                }
                is StateHandler.Error -> {
                    binding.progressBar.visibility = ProgressBar.GONE
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }
                is StateHandler.Success -> {
                    binding.progressBar.visibility = ProgressBar.GONE
                    binding.followersRv.visibility = View.VISIBLE
                    response.data?.let { it ->
                        binding.followersRv.adapter = mAdapter
                        mAdapter.apply {
                            if (it.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    getString(R.string.followers_not_found),
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            } else
                                setUsers(it)

                        }

                    }
                }
            }
        }

        return binding.root
    }

    companion object {
        fun newInstance(username: String): UserFollower {
            val args = Bundle(1)
            args.putString(UserDetail.USERNAME, username)
            val f = UserFollower()
            f.arguments = args
            return f
        }
    }
}