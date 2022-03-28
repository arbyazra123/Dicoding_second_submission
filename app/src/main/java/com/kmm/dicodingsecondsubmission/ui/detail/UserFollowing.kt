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
import com.kmm.dicodingsecondsubmission.databinding.FragmentUserFollowingBinding
import com.kmm.dicodingsecondsubmission.ui.home.GithubUserAdapter
import com.kmm.dicodingsecondsubmission.utlity.StateHandler

class UserFollowing : Fragment() {

    private lateinit var binding: FragmentUserFollowingBinding
    private lateinit var mAdapter: GithubUserAdapter
    private val userViewModel by viewModels<UserViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserFollowingBinding.inflate(layoutInflater)
        mAdapter = GithubUserAdapter(activity?.applicationContext)
        binding.followingsRv.layoutManager = LinearLayoutManager(context)
        binding.followingsRv.adapter = mAdapter
        val url = arguments?.getString(UserDetail.USERNAME)
        url?.let {
            userViewModel.getUserFollowings(it)
        }


        userViewModel.userFollowings.observe(this) { response ->
            when (response) {
                is StateHandler.Loading -> {
                    binding.followingsRv.visibility = View.GONE
                    binding.followingsRv.adapter = mAdapter
                    mAdapter.clearUsers()
                    binding.progressBar.visibility = ProgressBar.VISIBLE

                }
                is StateHandler.Error -> {
                    binding.progressBar.visibility = ProgressBar.GONE
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }
                is StateHandler.Success -> {
                    binding.progressBar.visibility = ProgressBar.GONE
                    binding.followingsRv.visibility = View.VISIBLE
                    response.data?.let { it ->
                        binding.followingsRv.adapter = mAdapter
                        mAdapter.apply {
                            if (it.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    getString(R.string.followings_not_found),
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

        fun newInstance(username: String): UserFollowing {
            val args = Bundle(1)
            args.putString(UserDetail.USERNAME, username)
            val f = UserFollowing()
            f.arguments = args
            return f
        }
    }

}