package com.keanequibilan.githubexplorer.viewmodel

import androidx.lifecycle.MutableLiveData
import com.keanequibilan.githubexplorer.model.Repo
import com.keanequibilan.githubexplorer.model.User
import com.keanequibilan.githubexplorer.network.RetrofitClient
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response

private const val FAKE_USER_NAME = "keane"
private const val FAKE_ERROR_CODE_USER = 416
private const val FAKE_ERROR_CODE_REPOS = 647

@Suppress("DeferredResultUnused")
@ExperimentalCoroutinesApi
@DisplayName("GitHubViewModel")
internal class GitHubViewModelTest {
    private lateinit var viewModel: GitHubViewModel

    private val coroutineContext = Dispatchers.Unconfined

    @Mock
    private lateinit var mockRetrofitClient: RetrofitClient

    @Mock
    private lateinit var mockUserLiveData: MutableLiveData<User>

    @Mock
    private lateinit var mockErrorLiveData: MutableLiveData<Int>

    @Mock
    private lateinit var mockReposLiveData: MutableLiveData<List<Repo>>

    @Mock
    private lateinit var mockSelectedRepoLiveData: MutableLiveData<Repo>

    @Mock
    private lateinit var mockRepo: Repo

    @Mock
    private lateinit var mockUser: User

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModel = GitHubViewModel(
            mockRetrofitClient,
            coroutineContext,
            mockUserLiveData,
            mockErrorLiveData,
            mockReposLiveData,
            mockSelectedRepoLiveData
        )
    }

    @Nested
    @DisplayName("getting Live Data should return")
    inner class GetLiveData {
        @Test
        @DisplayName("injected User LiveData")
        fun getUserLiveDataShouldReturnCorrectLiveData() {
            val result = viewModel.getUserLiveData()

            assertSame(mockUserLiveData, result)
        }

        @Test
        @DisplayName("injected Error LiveData")
        fun getErrorLiveDataShouldReturnCorrectLiveData() {
            val result = viewModel.getErrorLiveData()

            assertSame(mockErrorLiveData, result)
        }

        @Test
        @DisplayName("injected Repos List LiveData")
        fun getReposLiveDataShouldReturnCorrectLiveData() {
            val result = viewModel.getReposLiveData()

            assertSame(mockReposLiveData, result)
        }

        @Test
        @DisplayName("injected Repo LiveData")
        fun getSelectedRepoLiveDataShouldReturnCorrectLiveData() {
            val result = viewModel.getSelectedRepoLiveData()

            assertSame(mockSelectedRepoLiveData, result)
        }
    }

    @Nested
    @DisplayName("setting the selected repo")
    inner class SetSelectedRepo {
        @Test
        @DisplayName("should invoke the correct live data")
        fun setSelectedRepoShouldInvokeCorrectLiveData() {
            viewModel.setSelectedRepo(mockRepo)

            verify(mockSelectedRepoLiveData).value = mockRepo
        }
    }

    @Nested
    @DisplayName("loading the user")
    inner class LoadUser {

        @Mock
        private lateinit var mockDeferredUserResponse: Deferred<Response<User>>

        @Mock
        private lateinit var mockDeferredReposResponse: Deferred<Response<List<Repo>>>

        @Mock
        private lateinit var mockUserResponse: Response<User>

        @Mock
        private lateinit var mockReposResponse: Response<List<Repo>>

        @BeforeEach
        fun setup() = runBlocking {
            MockitoAnnotations.initMocks(this@LoadUser)

            `when`(mockRetrofitClient.getUserAsync(FAKE_USER_NAME))
                .thenReturn(mockDeferredUserResponse)
            `when`(mockRetrofitClient.getReposAsync(FAKE_USER_NAME))
                .thenReturn(mockDeferredReposResponse)

            `when`(mockDeferredUserResponse.await())
                .thenReturn(mockUserResponse)
            `when`(mockDeferredReposResponse.await())
                .thenReturn(mockReposResponse)

            `when`(mockUserResponse.code())
                .thenReturn(FAKE_ERROR_CODE_USER)
            `when`(mockUserResponse.body())
                .thenReturn(mockUser)

            `when`(mockReposResponse.code())
                .thenReturn(FAKE_ERROR_CODE_REPOS)
            `when`(mockReposResponse.body())
                .thenReturn(listOf(mockRepo))

            Unit
        }

        @Test
        @DisplayName("should call the right methods on the retrofit client")
        fun loadUserShouldCallTheRightMethodsOnRetrofitClient() {
            viewModel.loadUser(FAKE_USER_NAME)

            verify(mockRetrofitClient).getUserAsync(FAKE_USER_NAME)
            verify(mockRetrofitClient).getReposAsync(FAKE_USER_NAME)
        }

        @Test
        @DisplayName("should call error if user is unsuccessful")
        fun loadUserShouldCallErrorWhenUserErrors() = runBlocking {
            `when`(mockUserResponse.isSuccessful).thenReturn(false)
            `when`(mockReposResponse.isSuccessful).thenReturn(false)

            viewModel.loadUser(FAKE_USER_NAME)

            verify(mockErrorLiveData).postValue(FAKE_ERROR_CODE_USER)
            verify(mockErrorLiveData, never()).postValue(FAKE_ERROR_CODE_REPOS)

            Unit
        }

        @Test
        @DisplayName("should call error if user is successful but repo is unsuccessful")
        fun loadUserShouldCallErrorWhenRepoErrors() = runBlocking {
            `when`(mockUserResponse.isSuccessful).thenReturn(true)
            `when`(mockReposResponse.isSuccessful).thenReturn(false)

            viewModel.loadUser(FAKE_USER_NAME)

            verify(mockErrorLiveData, never()).postValue(FAKE_ERROR_CODE_USER)
            verify(mockErrorLiveData).postValue(FAKE_ERROR_CODE_REPOS)

            Unit
        }

        @Test
        @DisplayName("should post User data to User Live Data if successful")
        fun loadUserShouldPostUser() = runBlocking {
            `when`(mockUserResponse.isSuccessful).thenReturn(true)
            `when`(mockReposResponse.isSuccessful).thenReturn(true)

            viewModel.loadUser(FAKE_USER_NAME)

            verify(mockUserLiveData).postValue(mockUser)

            Unit
        }

        @Test
        @DisplayName("should post Repos data to Repos Live Data if successful")
        fun loadUserShouldPostRepos() = runBlocking {
            `when`(mockUserResponse.isSuccessful).thenReturn(true)
            `when`(mockReposResponse.isSuccessful).thenReturn(true)

            viewModel.loadUser(FAKE_USER_NAME)

            verify(mockReposLiveData).postValue(listOf(mockRepo))

            Unit
        }
    }
}