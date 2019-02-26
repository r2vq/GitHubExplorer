package com.keanequibilan.githubexplorer.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.keanequibilan.githubexplorer.R
import com.keanequibilan.githubexplorer.model.User
import com.keanequibilan.githubexplorer.viewmodel.GitHubViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val gitHubViewModel: GitHubViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gitHubViewModel
            .getUser()
            .observe(this, Observer { setUser(it) })

        btn_search
            .setOnClickListener {
                gitHubViewModel.loadUser(et_search.text.toString())
            }
    }

    private fun setUser(user: User) {
        Toast.makeText(this, "User found: ${user.name}", Toast.LENGTH_SHORT).show()
    }
}
