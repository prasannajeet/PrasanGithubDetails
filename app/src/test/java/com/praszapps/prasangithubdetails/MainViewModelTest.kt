package com.praszapps.prasangithubdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.Dispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: MainViewModel

    @MockK
    private lateinit var repo: GithubRepository

    @MockK
    private lateinit var uiStateObserver: Observer<UIState<RepoListAdapter>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = MainViewModel()
    }


    @Test
    fun whenServer200ReturnListOfRepos() {

        testCoroutineRule.testDispatcher.runBlockingTest {
            coEvery { repo.getGithubRepositoryForUser(any()) } returns APICallResult.OnSuccessResponse<List<GithubRepo>>(listOf(GithubRepo("Hi"), GithubRepo("Bye")))

            viewModel.repoListApiCallObserverLiveData.observeForever(uiStateObserver)
            viewModel.getRepoList("Prasan")


            coVerify { repo.getGithubRepositoryForUser(any()) }

            verify { uiStateObserver.onChanged(UIState.Loading) }
            verify { uiStateObserver.onChanged(UIState.OnOperationSuccess<RepoListAdapter>(any())) }

            viewModel.repoListApiCallObserverLiveData.removeObserver(uiStateObserver)
        }
    }
}