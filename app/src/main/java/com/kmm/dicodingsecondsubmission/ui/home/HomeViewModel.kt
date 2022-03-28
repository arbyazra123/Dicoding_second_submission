package com.kmm.dicodingsecondsubmission.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmm.dicodingsecondsubmission.data.api.APIConfig
import com.kmm.dicodingsecondsubmission.data.model.SearchResponse
import com.kmm.dicodingsecondsubmission.utlity.StateHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

    private val _searchResult = MutableLiveData<StateHandler<SearchResponse>>()
    val searchResult: LiveData<StateHandler<SearchResponse>> get() = _searchResult

    fun searchUser(query: String) {
        _searchResult.postValue(StateHandler.Loading())
        val client = APIConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    _searchResult.postValue(StateHandler.Success(response.body()))
                } else {
                    Log.e(TAG, "Unsuccessful : ${response.message()}")
                    _searchResult.postValue(StateHandler.Error("Unsuccessful : ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")

                _searchResult.postValue(StateHandler.Error("onFailure: ${t.message.toString()}"))
            }
        })
    }
}