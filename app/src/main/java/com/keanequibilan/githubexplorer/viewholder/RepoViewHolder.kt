package com.keanequibilan.githubexplorer.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.keanequibilan.githubexplorer.model.Repo
import com.keanequibilan.githubexplorer.viewmodel.GitHubViewModel
import kotlinx.android.synthetic.main.list_item_repo.view.*

class RepoViewHolder(
    itemView: View,
    gitHubViewModel: GitHubViewModel
) : RecyclerView.ViewHolder(itemView) {

    private var repo: Repo? = null

    init {
        itemView.setOnClickListener { repo?.let { gitHubViewModel.setSelectedRepo(it) } }
    }

    fun bind(repo: Repo) {
        this.repo = repo
        itemView.title.text = repo.name
        itemView.description.text = repo.description
    }
}