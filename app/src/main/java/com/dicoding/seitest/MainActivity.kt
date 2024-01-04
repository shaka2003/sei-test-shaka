package com.dicoding.seitest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.seitest.databinding.ActivityMainBinding
import com.dicoding.seitest.listUser.ListUserActivity
import com.dicoding.seitest.login.LoginActivity
import com.dicoding.seitest.register.AddUserActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnLogin = binding.btnLogin
        val btnAdd = binding.btnAddUser
        val btnList = binding.btnListUser

        btnLogin.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        btnAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddUserActivity::class.java))
        }

        btnList.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListUserActivity::class.java))
        }
    }
}