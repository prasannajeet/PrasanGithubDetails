package com.praszapps.prasangithubdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private val result = APICallResult.OnSuccessResponse(listOf(GithubRepo("Hi")))
    private val uiResult =
        UIState.OnOperationSuccess<RepoListAdapter>(RepoListAdapter(listOf(GithubRepo("Hi"))))
    private val exception = UIState.OnOperationFailed(Exception())
    private val name = "Prasan"

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var mockRepo: GithubRepository

    @Mock
    private lateinit var uiStateObserver: Observer<UIState<RepoListAdapter>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel()
    }


    @Test
    fun whenServer200ReturnListOfRepos() {

        testCoroutineRule.testDispatcher.runBlockingTest {

            val viewModel = MainViewModel()
            viewModel.getRepoList(name)

            viewModel.repoListApiCallObserverLiveData.observeForever(uiStateObserver)
            verify(uiStateObserver).onChanged(UIState.Loading)
            viewModel.repoListApiCallObserverLiveData.removeObserver(uiStateObserver)
        }
    }
}