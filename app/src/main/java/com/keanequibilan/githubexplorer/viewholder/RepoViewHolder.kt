package com.keanequibilan.githubexplorer.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.keanequibilan.githubexplorer.model.Repo
import kotlinx.android.synthetic.main.list_item_repo.view.*

class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(repo: Repo) {
        itemView.title.text = repo.name
        itemView.description.text = repo.description
    }
}