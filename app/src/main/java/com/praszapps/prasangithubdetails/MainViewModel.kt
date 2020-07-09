package com.praszapps.prasangithubdetails

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val repoListLiveData: MutableLiveData<List<GithubRepo>> = MutableLiveData()

    fun getRepoList(@NonNull username: String) {

        viewModelScope.launch {
            when(val repoCallResult = GithubRepository().getGithubRepositoryForUser(username)) {
                is Result.Success -> repoListLiveData.postValue(repoCallResult.data)
                else -> Log.d("Oops", "Something went wrong")
            }
        }
    }
}