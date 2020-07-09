package com.praszapps.prasangithubdetails

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubClient {

    @GET("/users/{user}/repos")
    suspend fun reposForUser(@Path("user") userID: String): Response<List<GithubRepo>>

}