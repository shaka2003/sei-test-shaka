package com.dicoding.seitest.listUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.seitest.API.response.DataItem
import com.dicoding.seitest.R

class UserAdapter(var userList: List<DataItem?>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvId: TextView = itemView.findViewById(R.id.tv_id)
        val tvUserName: TextView = itemView.findViewById(R.id.tv_username)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val currentUser = userList[position]
        val id = currentUser?.userid.toString()
        val username = currentUser?.username.toString()
        val name = currentUser?.name.toString()
        val email = currentUser?.email.toString()

        holder.tvId.text = "User ID: $id"
        holder.tvUserName.text = "Username: $username"
        holder.tvName.text = "Name: $name"
        holder.tvEmail.text = "Email: $email"
    }

    override fun getItemCount(): Int = userList.size
}