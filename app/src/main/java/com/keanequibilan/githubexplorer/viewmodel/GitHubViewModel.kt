package com.keanequibilan.githubexplorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keanequibilan.githubexplorer.model.User
import com.keanequibilan.githubexplorer.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * A [ViewModel] that stores and manages [User] data. Retrieve the [User] from the network with [loadUser]. All results
 * of the network calls are posted to the [LiveData] that can be observed by calling [getUserLiveData].
 */
class GitHubViewModel(
    private val retrofitClient: RetrofitClient,
    private val backgroundContext: CoroutineContext
) : ViewModel() {
    private val user: MutableLiveData<User> = MutableLiveData()
    private val error: MutableLiveData<Int> = MutableLiveData()

    /**
     * Returns a [LiveData] that notifies observers of updated [User]s.
     */
    fun getUserLiveData(): LiveData<User> = user

    /**
     * Returns a [LiveData] that notifies observers of updated Status Codes when there is an error.
     */
    fun getErrorLiveData(): LiveData<Int> = error

    /**
     * Does a network call to retrieve the user associated with the [name] passed in. To receive the result of the call
     * subscribe to the [LiveData] retrieved by [getUserLiveData].
     */
    fun loadUser(name: String) = CoroutineScope(backgroundContext).launch {
        retrofitClient
            .getUser(name)
            .let {
                if (it.isSuccessful) {
                    user.postValue(it.body())
                } else {
                    error.postValue(it.code())
                }
            }
    }
}