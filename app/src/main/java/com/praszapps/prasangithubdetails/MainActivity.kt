package com.praszapps.prasangithubdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.praszapps.prasangithubdetails.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: MainViewModel by viewModels()

        viewModel.repoListLiveData.observe(this, Observer {
            binding.githubRepoList.adapter = RepoListAdapter(it)
        })
        viewModel.getRepoList("Prasannajeet")
    }
}