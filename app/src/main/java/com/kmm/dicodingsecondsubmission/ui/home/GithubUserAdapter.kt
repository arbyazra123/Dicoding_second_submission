package com.kmm.dicodingsecondsubmission.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kmm.dicodingsecondsubmission.R
import com.kmm.dicodingsecondsubmission.data.model.UserItem
import com.kmm.dicodingsecondsubmission.utlity.AdapterClickListener
import java.util.ArrayList

class GithubUserAdapter internal constructor(private val ctx: Context?) :
    RecyclerView.Adapter<GithubUserAdapter.ViewHolder>() {
    var listener: AdapterClickListener? = null
    private var users: ArrayList<UserItem> = ArrayList()
    fun setUsers(users: ArrayList<UserItem>) {
        this.users = users
    }

    fun clearUsers() {
        users.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GithubUserAdapter.ViewHolder, position: Int) {
        holder.binding(users[position], holder.itemView)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(it, users[position])
        }
    }

    override fun getItemCount(): Int = users.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val username: TextView = view.findViewById(R.id.username)
        private val score: TextView = view.findViewById(R.id.user_score)
        private val imageM: ImageView = view.findViewById(R.id.item_image)
        fun binding(user: UserItem, view: View) {
            username.text = user.login
            score.text = ctx?.getString(R.string.username, user.score.toString())
            user.avatarUrl.let {
                Glide.with(view).load(it).centerCrop().into(imageM)
            }
        }

    }


}