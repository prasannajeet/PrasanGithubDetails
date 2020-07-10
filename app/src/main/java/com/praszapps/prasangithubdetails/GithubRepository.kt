package com.praszapps.prasangithubdetails

import androidx.annotation.NonNull
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GithubRepository {

    private val retrofitClient: GitHubClient by lazy {
        Retrofit.Builder().run {
            baseUrl("https://api.github.com/")
            addConverterFactory(MoshiConverterFactory.create())
            build()
        }.run {
            create(GitHubClient::class.java)
        }
    }

    suspend fun getGithubRepositoryForUser(@NonNull name: String): APICallResult<List<GithubRepo>> {
        return safeApiCall("You sure $name is the correct user here?") {
            retrofitClient.reposForUser(
                name
            )
        }
    }
}