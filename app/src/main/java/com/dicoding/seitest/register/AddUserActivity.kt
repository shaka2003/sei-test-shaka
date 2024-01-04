package com.dicoding.seitest.register

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.dicoding.ViewModelFactory
import com.dicoding.seitest.databinding.ActivityAddUserBinding
import com.dicoding.seitest.listUser.ListUserActivity
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbarAddUser
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pb = binding.pb
        val edUsername = binding.edRegisterUsername
        val edPass = binding.edRegisterPassword
        val edName = binding.edRegisterName
        val edEmail = binding.edRegisterEmail
        val btnRegis = binding.btnAdd

        viewModel.isLoading.observe(this) {
            binding.pb.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.isRegistrationSuccessful.observe(this, Observer { isSuccessful ->
            if (isSuccessful) {
                val intent = Intent(this, ListUserActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        btnRegis.setOnClickListener {
            val username = edUsername.text.toString()
            val pass = edPass.text.toString()
            val name = edName.text.toString()
            val email = edEmail.text.toString()
            val errorEmail = binding.edRegisterEmail.isError
            val errorPass = binding.edRegisterPassword.isError

            if (username.isEmpty() || pass.isEmpty() || name.isEmpty() || email.isEmpty()){
                Toast.makeText(this, "please fill all the field", Toast.LENGTH_SHORT).show()
            } else if (username.isEmpty()) {
                Toast.makeText(this, "please fill username", Toast.LENGTH_SHORT).show()
            } else if (pass.isEmpty()) {
                Toast.makeText(this, "please fill password", Toast.LENGTH_SHORT).show()
            } else if (name.isEmpty()) {
                Toast.makeText(this, "please fill name", Toast.LENGTH_SHORT).show()
            } else if (email.isEmpty()) {
                Toast.makeText(this, "please fill email", Toast.LENGTH_SHORT).show()
            } else if (errorEmail) {
                Toast.makeText(this, "please fill the correct email format", Toast.LENGTH_SHORT).show()
            } else if (errorPass) {
                Toast.makeText(this, "password must be 8 characters", Toast.LENGTH_SHORT).show()
            } else {
                val json = """
                            {
                                "username": "$username",
                                "password": "$pass",
                                "name": "$name",
                                "email": "$email"
                            }
                        """.trimIndent()

                val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

                lifecycleScope.launch {
                    viewModel.register(requestBody)
                }
            }
        }
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