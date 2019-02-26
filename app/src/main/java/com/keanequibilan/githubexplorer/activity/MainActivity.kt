package com.keanequibilan.githubexplorer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.keanequibilan.githubexplorer.R
import com.keanequibilan.githubexplorer.adapter.RepoListAdapter
import com.keanequibilan.githubexplorer.fragment.RepoDetailsBottomSheetDialogFragment
import com.keanequibilan.githubexplorer.model.Repo
import com.keanequibilan.githubexplorer.model.User
import com.keanequibilan.githubexplorer.viewmodel.GitHubViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val gitHubViewModel: GitHubViewModel by viewModel()

    private val repoListAdapter: RepoListAdapter by inject { parametersOf(gitHubViewModel) }

    private var repoDetailsBottomSheetDialogFragment: BottomSheetDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gitHubViewModel
            .getUserLiveData()
            .observe(this, Observer { setUser(it) })

        gitHubViewModel
            .getErrorLiveData()
            .observe(this, Observer { setError(it) })

        gitHubViewModel
            .getReposLiveData()
            .observe(this, Observer { setRepos(it) })

        gitHubViewModel
            .getSelectedRepoLiveData()
            .observe(this, Observer { showRepoDetails(it) })

        rv_repos.adapter = repoListAdapter

        btn_search.setOnClickListener { gitHubViewModel.loadUser(et_search.text.toString()) }
    }

    private fun setError(code: Int) {
        iv_avatar.setImageResource(android.R.color.transparent)
        tv_user_name.text = getString(R.string.error_message, code)
    }

    private fun setRepos(repos: List<Repo>) = repoListAdapter.submitList(repos)

    private fun setUser(user: User) {
        Glide
            .with(this)
            .load(user.avatarUrl)
            // TODO - Animate items in order
            .into(iv_avatar)
        tv_user_name.text = user.name
    }

    private fun showRepoDetails(repo: Repo?) = repo?.let {
        gitHubViewModel.setSelectedRepo(null)
        repoDetailsBottomSheetDialogFragment = RepoDetailsBottomSheetDialogFragment
            .showDialog(supportFragmentManager, it)
    }
}
