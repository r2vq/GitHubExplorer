package com.keanequibilan.githubexplorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keanequibilan.githubexplorer.model.Repo
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
    private val userLiveData: MutableLiveData<User> = MutableLiveData()
    private val errorLiveData: MutableLiveData<Int> = MutableLiveData()
    private val reposLiveData: MutableLiveData<List<Repo>> = MutableLiveData()
    private val selectedRepoLiveData: MutableLiveData<Repo> = MutableLiveData()

    /**
     * Returns a [LiveData] that notifies observers of updated [User]s.
     */
    fun getUserLiveData(): LiveData<User> = userLiveData

    /**
     * Returns a [LiveData] that notifies observers of updated Status Codes when there is an errorLiveData.
     */
    fun getErrorLiveData(): LiveData<Int> = errorLiveData

    /**
     * Returns a [LiveData] that notifies observers of updated [Repo] lists.
     */
    fun getReposLiveData(): LiveData<List<Repo>> = reposLiveData

    fun getSelectedRepoLiveData(): LiveData<Repo> = selectedRepoLiveData

    fun setSelectedRepo(repo: Repo) {
        selectedRepoLiveData.value = repo
    }

    /**
     * Does a network call to retrieve the userLiveData associated with the [name] passed in. To receive the result of
     * the calls subscribe to the [LiveData] retrieved by [getUserLiveData], [getReposLiveData], and [getErrorLiveData].
     */
    fun loadUser(name: String) = CoroutineScope(backgroundContext).launch {
        retrofitClient.apply {
            val userDeferred = getUserAsync(name)
            val reposDeferred = getReposAsync(name)

            val user = userDeferred.await()
            val repos = reposDeferred.await()

            if (!user.isSuccessful) {
                errorLiveData.postValue(user.code())
            } else if (!repos.isSuccessful) {
                errorLiveData.postValue(repos.code())
            } else {
                userLiveData.postValue(user.body())
                reposLiveData.postValue(repos.body())
            }
        }
    }
}