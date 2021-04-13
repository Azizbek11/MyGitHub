package com.azizbek.mygithub.adapter

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.azizbek.mygithub.R
import com.azizbek.mygithub.model.Items
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

class UserRepoAdapter(private val context: Context, private var items: List<Items>?)
    : RecyclerView.Adapter<UserRepoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_repo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text = items!![position].owner.login
        holder.repoName.text = items!![position].name
        holder.repoLanguage.text = items!![position].language
        holder.starCount.text = (items!![position].stargazersCount).toString()

        holder.shareCode.setOnClickListener {
            val share = Intent(Intent.ACTION_SEND)
            share.putExtra(Intent.EXTRA_TEXT,"Hey friend! Check out this repository:)\n"+items!![position].htmlUrl)
            share.type = "text/plain"
            context.startActivity(share)
        }

        holder.goGitHub.setOnClickListener {
            val go = Intent(Intent.ACTION_VIEW)
            go.data = Uri.parse(items!![position].htmlUrl)
            context.startActivity(go)
        }

            Glide.with(context)
                .load(items!![position].owner.avatarUrl)
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .apply(RequestOptions().circleCrop())
                .into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return if (items != null) {
            items!!.size
        } else 0
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var repoName: TextView=view.findViewById(R.id.repoName)
        var starCount: TextView=view.findViewById(R.id.starCount)
        var repoLanguage: TextView=view.findViewById(R.id.repoLanguage)
        var imageView: ImageView =view.findViewById(R.id.repoUserImage)
        var goGitHub: ImageView =view.findViewById(R.id.goGitHub)
        var shareCode: ImageView =view.findViewById(R.id.shareCode)
        var userName: TextView=view.findViewById(R.id.userName)

    }
}