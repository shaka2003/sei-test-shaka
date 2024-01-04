package com.dicoding.seitest.login

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import com.dicoding.ViewModelFactory
import com.dicoding.seitest.R
import com.dicoding.seitest.databinding.ActivityLoginBinding
import com.dicoding.seitest.listUser.ListUserActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var edUsername: TextInputEditText
    private lateinit var edPass: TextInputEditText
    private lateinit var pb: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbarLogin
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        edUsername = binding.edLoginUsername
        edPass = binding.edLoginPassword
        pb = binding.pb

        val tvUsername = binding.tvUsername
        val tvName = binding.tvName
        val tvEmail = binding.tvEmail

        viewModel.isLoading.observe(this) {
            pb.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.loginResult.observe(this, Observer {
            pb.visibility = View.VISIBLE
            if (it.data != null) {
                val data = it.data
                val username = data.username
                val name = data.name
                val email = data.email

                tvUsername.text = "Username: $username"
                tvName.text = "Name: $name"
                tvEmail.text = "Email: $email"

                showResultPopUp(username, name, email)

            } else {
                // If the login was not successful, show an error message
                Toast.makeText(this, it.message ?: "Login failed", Toast.LENGTH_SHORT).show()
            }
            pb.visibility = View.GONE
        })

        val btnLogin = binding.btnLogin
        btnLogin.setOnClickListener {
            val username = edUsername.text.toString()
            val pass = edPass.text.toString()
            val passError = binding.edLoginPassword.isError

            if (username.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "please fill all the field", Toast.LENGTH_SHORT).show()
            } else if (username.isEmpty()){
                Toast.makeText(this, "please fill the username", Toast.LENGTH_SHORT).show()
            } else if (pass.isEmpty()){
                Toast.makeText(this, "please fill the password", Toast.LENGTH_SHORT).show()
            } else if (passError){
                Toast.makeText(this, "password must be 8 characters", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(username, pass)
            }
        }
    }

    private fun showResultPopUp(usernamePar: String?, namePar: String?, emailPar: String?){
        val builder = AlertDialog.Builder(this)

        val customView = LayoutInflater.from(this).inflate(R.layout.login_result, null)
        builder.setView(customView)

        val username = customView.findViewById<TextView>(R.id.tv_username)
        val name = customView.findViewById<TextView>(R.id.tv_name)
        val email = customView.findViewById<TextView>(R.id.tv_email)

        username.text = "Username: $usernamePar"
        name.text = "Name: $namePar"
        email.text = "Email: $emailPar"

        val btnNext = customView.findViewById<Button>(R.id.btnNext)
        val btnClose = customView.findViewById<Button>(R.id.btnClose)

        val dialog = builder.create()

        btnNext.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ListUserActivity::class.java))
            finish()
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
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