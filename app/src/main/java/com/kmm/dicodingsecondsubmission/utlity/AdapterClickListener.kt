package com.kmm.dicodingsecondsubmission.utlity

import android.view.View
import com.kmm.dicodingsecondsubmission.data.model.UserItem

interface AdapterClickListener {
    fun onItemClick(v:View, user: UserItem)
}