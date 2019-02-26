package com.keanequibilan.githubexplorer.util

import androidx.recyclerview.widget.DiffUtil
import com.keanequibilan.githubexplorer.model.Repo

class RepoDiffUtilItemCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
        oldItem.name == newItem.name


    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
        oldItem == newItem
}