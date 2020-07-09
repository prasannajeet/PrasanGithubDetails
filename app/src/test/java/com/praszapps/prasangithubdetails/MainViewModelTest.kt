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
    private lateinit var apiUsersObserver: Observer<List<GithubRepo>>

    @Test
    fun whenServer200ReturnListOfRepos() {
        testCoroutineRule.runBlockingTest {
            doReturn(listOf(GithubRepo("Hi")))
                .`when`(repo).getGithubRepositoryForUser(ArgumentMatchers.anyString())

            val viewModel = MainViewModel()
            viewModel.repoListLiveData.observeForever(apiUsersObserver)
            verify(repo).getGithubRepositoryForUser(ArgumentMatchers.anyString())
            verify(apiUsersObserver).onChanged(listOf(GithubRepo("Hi")))
            viewModel.repoListLiveData.removeObserver(apiUsersObserver)
        }
    }
}