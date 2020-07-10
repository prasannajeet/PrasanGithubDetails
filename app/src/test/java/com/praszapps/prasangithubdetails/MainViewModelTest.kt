package com.praszapps.prasangithubdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repo: GithubRepository

    @Mock
    private lateinit var uiStateObserver: Observer<UIState<RepoListAdapter>>

    @Test
    fun whenServer200ReturnListOfRepos() {
        testCoroutineRule.runBlockingTest {
            doReturn(listOf(GithubRepo("Hi")))
                .`when`(repo).getGithubRepositoryForUser(ArgumentMatchers.anyString())

            val viewModel = MainViewModel()

            viewModel.repoListApiCallObserverLiveData.observeForever(uiStateObserver)
            verify(repo).getGithubRepositoryForUser(ArgumentMatchers.anyString())
            verify(uiStateObserver).onChanged(UIState.Loading)
            verify(uiStateObserver).onChanged(UIState.OnOperationSuccess<RepoListAdapter>(any(RepoListAdapter::class.java)))
            viewModel.repoListApiCallObserverLiveData.removeObserver(uiStateObserver)
        }
    }
}