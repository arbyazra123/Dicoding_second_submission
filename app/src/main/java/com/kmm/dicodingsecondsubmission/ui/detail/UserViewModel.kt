package com.kmm.dicodingsecondsubmission.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmm.dicodingsecondsubmission.data.api.APIConfig
import com.kmm.dicodingsecondsubmission.data.model.UserItem
import com.kmm.dicodingsecondsubmission.data.model.UserResponse
import com.kmm.dicodingsecondsubmission.utlity.StateHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    companion object {
        val TAG: String? = UserViewModel::class.java.simpleName
    }

    private val _userDetail = MutableLiveData<StateHandler<UserResponse>>()
    val userDetail: LiveData<StateHandler<UserResponse>> get() = _userDetail

    private val _userFollowers = MutableLiveData<StateHandler<ArrayList<UserItem>>>()
    val userFollowers: LiveData<StateHandler<ArrayList<UserItem>>> get() = _userFollowers

    private val _userFollowings = MutableLiveData<StateHandler<ArrayList<UserItem>>>()
    val userFollowings: LiveData<StateHandler<ArrayList<UserItem>>> get() = _userFollowings

    fun getUser(username: String) {
        val service = APIConfig.getApiService().user(username)
        service.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    _userDetail.postValue(StateHandler.Success(response.body()))
                } else {
                    Log.e(TAG, "Unsuccessful : ${response.message()}")
                    _userDetail.postValue(StateHandler.Error("Unsuccessful : ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")

                _userDetail.postValue(StateHandler.Error("onFailure: ${t.message.toString()}"))
            }
        })
    }

    fun getUserFollowers(username: String) {
        val service = APIConfig.getApiService().userFollowers(username)
        service.enqueue(object : Callback<List<UserItem>> {
            override fun onResponse(call: Call<List<UserItem>>, response: Response<List<UserItem>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        print("it.size ${it.size}")
                        _userFollowers.postValue(StateHandler.Success(ArrayList(it)))
                    }

                } else {
                    Log.e(TAG, "Unsuccessful : ${response.message()}")
                    _userFollowers.postValue(StateHandler.Error("Unsuccessful : ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")

                _userFollowers.postValue(StateHandler.Error("onFailure: ${t.message.toString()}"))
            }
        })
    }

    fun getUserFollowings(username: String) {
        val service = APIConfig.getApiService().userFollowings(username)
        service.enqueue(object : Callback<List<UserItem>> {
            override fun onResponse(call: Call<List<UserItem>>, response: Response<List<UserItem>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        print("it.size ${it.size}")
                        _userFollowings.postValue(StateHandler.Success(ArrayList(it)))
                    }
                } else {
                    Log.e(TAG, "Unsuccessful : ${response.message()}")
                    _userFollowings.postValue(StateHandler.Error("Unsuccessful : ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")

                _userFollowings.postValue(StateHandler.Error("onFailure: ${t.message.toString()}"))
            }
        })
    }
}