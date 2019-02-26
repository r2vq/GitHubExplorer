package com.keanequibilan.githubexplorer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.keanequibilan.githubexplorer.R
import com.keanequibilan.githubexplorer.model.Repo
import com.keanequibilan.githubexplorer.viewholder.RepoViewHolder

class RepoListAdapter(itemCallback: DiffUtil.ItemCallback<Repo>) : ListAdapter<Repo, RepoViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.list_item_repo, parent, false)
        .let {
            RepoViewHolder(it)
        }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        getItem(position).let { holder.bind(it) }
    }
}