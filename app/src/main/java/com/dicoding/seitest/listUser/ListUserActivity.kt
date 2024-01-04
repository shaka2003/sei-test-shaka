package com.dicoding.seitest.listUser

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ViewModelFactory
import com.dicoding.seitest.databinding.ActivityListUserBinding

class ListUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListUserBinding
    private lateinit var rvUser: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private val viewModel by viewModels<UserViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var pb: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbarListUser
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pb = binding.pb

        viewModel.isLoading.observe(this) {
            pb.visibility = if (it) View.VISIBLE else View.GONE
        }

        rvUser = binding.rvUser
        rvUser.layoutManager = LinearLayoutManager(this)

        userAdapter = UserAdapter(listOf())
        rvUser.adapter = userAdapter

        // Call the getUsers function in the ViewModel
        viewModel.getUsers()

        // Observe the userResponse LiveData
        viewModel.userResponse.observe(this, Observer { response ->
            // Update the adapter's data
            userAdapter.userList = response.data ?: listOf()
            userAdapter.notifyDataSetChanged()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
