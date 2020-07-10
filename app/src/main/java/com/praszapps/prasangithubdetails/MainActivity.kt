package com.praszapps.prasangithubdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
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

        viewModel.repoListApiCallObserverLiveData.observe(this, Observer {

            // This approach helps keep the UI State managed in a sealed class that lets us define multiple
            // success data objects which we then make the UI respond to
            when(it) {

                is UIState.Loading -> binding.loadingBar.visibility = View.VISIBLE // Alternatively, use Data Binding and Binding adapters

                is UIState.OnOperationSuccess<RepoListAdapter> -> {
                    binding.loadingBar.visibility = View.GONE // Alternatively, use Data Binding and Binding adapters
                    binding.githubRepoList.adapter = it.output
                }

                is UIState.OnOperationFailed -> {
                    binding.loadingBar.visibility = View.GONE // Alternatively, use Data Binding and Binding adapters
                    Toast.makeText(this, it.exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.getRepoList("Prasannajeet")
    }
}