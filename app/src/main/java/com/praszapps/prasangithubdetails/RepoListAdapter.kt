package com.praszapps.prasangithubdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.praszapps.prasangithubdetails.databinding.RepoNameItemBinding

class RepoListAdapter(private val list: List<GithubRepo>) : RecyclerView.Adapter<RepoListAdapter.RepoListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder =
        RepoListViewHolder(RepoNameItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RepoListAdapter.RepoListViewHolder, position: Int) =
        holder.bind(list[position])

    class RepoListViewHolder(private val binding: RepoNameItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: GithubRepo) = binding.repoName.run {
            text = repo.name
        }
    }
}