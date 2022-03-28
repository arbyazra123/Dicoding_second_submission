package com.kmm.dicodingsecondsubmission.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmm.dicodingsecondsubmission.R
import com.kmm.dicodingsecondsubmission.data.model.UserItem
import com.kmm.dicodingsecondsubmission.databinding.FragmentHomePageBinding
import com.kmm.dicodingsecondsubmission.ui.detail.UserDetail
import com.kmm.dicodingsecondsubmission.utlity.AdapterClickListener
import com.kmm.dicodingsecondsubmission.utlity.OnBackHandler
import com.kmm.dicodingsecondsubmission.utlity.StateHandler
import java.util.ArrayList

class HomePage : Fragment(), AdapterClickListener {
    private lateinit var binding: FragmentHomePageBinding
    private val mainViewModel by viewModels<HomeViewModel>()
    private lateinit var mAdapter: GithubUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePageBinding.inflate(layoutInflater)
        mAdapter = GithubUserAdapter(activity?.applicationContext)
        mAdapter.listener = this
        binding.githubRv.layoutManager = LinearLayoutManager(context)
        binding.searchUser.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                search(binding.searchUser.text.toString())
            }
            false
        }
        binding.searchUseTl.setEndIconOnClickListener {
            search(binding.searchUser.text.toString())
        }
        mainViewModel.searchResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateHandler.Loading -> {
                    binding.githubRv.visibility = View.GONE
                    binding.githubRv.adapter = mAdapter
                    mAdapter.clearUsers()
                    binding.progressBar.visibility = ProgressBar.VISIBLE

                }
                is StateHandler.Error -> {
                    binding.progressBar.visibility = ProgressBar.GONE
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }
                is StateHandler.Success -> {
                    binding.progressBar.visibility = ProgressBar.GONE
                    binding.githubRv.visibility = View.VISIBLE
                    response.data?.let { it ->
                        binding.githubRv.adapter = mAdapter
                        mAdapter.apply {
                            print("Get Users ${it.items.size}")
                            if (it.items.isEmpty()) {
                                Toast.makeText(context, getString(R.string.user_not_found), Toast.LENGTH_LONG)
                                    .show()
                            } else
                                setUsers(it.items as ArrayList<UserItem>)

                        }

                    }
                }
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun search(q: String) {
        if (q.isEmpty())
            Toast.makeText(context, getString(R.string.search_null), Toast.LENGTH_LONG)
                .show()
        else
            mainViewModel.searchUser(q)
    }

    override fun onItemClick(v: View, user: UserItem) {
        parentFragmentManager.commit {
            val f = UserDetail()
            val data = Bundle()
            data.putParcelable(UserDetail.DATA, user)
            f.arguments = data
            addToBackStack(TAG)
            replace(R.id.container, f, UserDetail::class.java.simpleName)
        }
    }

    companion object {
        const val TAG = "home-page"
    }

}