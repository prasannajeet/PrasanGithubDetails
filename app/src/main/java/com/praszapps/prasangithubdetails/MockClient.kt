package com.praszapps.prasangithubdetails

import retrofit2.Response

class MockClient : GitHubClient {

    override suspend fun reposForUser(userID: String): Response<List<GithubRepo>> =
        Response.success(listOf<GithubRepo>(GithubRepo("Hello"), GithubRepo("Hi")))
}