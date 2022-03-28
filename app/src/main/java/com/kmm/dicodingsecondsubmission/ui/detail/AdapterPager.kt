package com.kmm.dicodingsecondsubmission.ui.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kmm.dicodingsecondsubmission.data.model.UserResponse

class AdapterPager(f: Fragment,user:UserResponse) : FragmentStateAdapter(f) {
    private val pages = listOf(
        UserFollower.newInstance(user.login),
        UserFollowing.newInstance(user.login),
    )


    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return pages[position]
    }
}