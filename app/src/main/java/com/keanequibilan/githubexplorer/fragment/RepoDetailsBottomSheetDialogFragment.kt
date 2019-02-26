package com.keanequibilan.githubexplorer.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.keanequibilan.githubexplorer.R
import com.keanequibilan.githubexplorer.model.Repo
import kotlinx.android.synthetic.main.fragment_repo_bottom_sheet_dialog.*
import java.text.SimpleDateFormat

private const val BUNDLE_KEY_UPDATED = "updated"
private const val BUNDLE_KEY_STARGAZERS = "stargazers"
private const val BUNDLE_KEY_FORKS = "forks"

class RepoDetailsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_repo_bottom_sheet_dialog,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            tv_value_updated.text = getString(BUNDLE_KEY_UPDATED)
            tv_value_stars.text = getString(BUNDLE_KEY_STARGAZERS)
            tv_value_forks.text = getString(BUNDLE_KEY_FORKS)
        }
    }

    companion object {
        /**
         * Shows the [RepoDetailsBottomSheetDialogFragment] as a dialog.
         *
         * @param supportFragmentManager FragmentManager used to show the fragment.
         * @param repo Repo used to get data for the fragment.
         * @return The instance of [RepoDetailsBottomSheetDialogFragment]
         */
        fun showDialog(supportFragmentManager: FragmentManager, repo: Repo) =
            RepoDetailsBottomSheetDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_KEY_UPDATED, repo.updatedAt.formatDate())
                    putString(BUNDLE_KEY_STARGAZERS, repo.stargazersCount.toString())
                    putString(BUNDLE_KEY_FORKS, repo.forks.toString())
                }
                show(supportFragmentManager, RepoDetailsBottomSheetDialogFragment::class.java.simpleName)
            }

        @SuppressLint("SimpleDateFormat")
        private val DATE_FORMAT_GITHUB = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
        @SuppressLint("SimpleDateFormat")
        private val DATE_FORMAT_OUTPUT = SimpleDateFormat("MMM dd, yyyy h:mm:ss a")

        private fun String.formatDate(): String = this
            .let { DATE_FORMAT_GITHUB.parse(it) }
            .let { DATE_FORMAT_OUTPUT.format(it) }
    }
}