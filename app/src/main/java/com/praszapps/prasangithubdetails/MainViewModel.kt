package com.praszapps.prasangithubdetails

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val repoListApiCallObserverLiveData: MutableLiveData<UIState<RepoListAdapter>> = MutableLiveData()

    fun getRepoList(@NonNull username: String) {

        repoListApiCallObserverLiveData.postValue(UIState.Loading)

        viewModelScope.launch {
            when(val repoCallResult = GithubRepository().getGithubRepositoryForUser(username)) {
                is APICallResult.OnSuccessResponse -> repoListApiCallObserverLiveData.postValue(UIState.OnOperationSuccess<RepoListAdapter>(RepoListAdapter(repoCallResult.data)))
                is APICallResult.OnErrorResponse -> repoListApiCallObserverLiveData.postValue(UIState.OnOperationFailed(repoCallResult.exception))
            }
        }
    }
}