package com.azizbek.mygithub.ui.account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azizbek.mygithub.R
import com.azizbek.mygithub.adapter.UserRepoAdapter
import com.azizbek.mygithub.authentication.AuthenticationActivity
import com.azizbek.mygithub.databinding.FragmentAccountBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_home.*

class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel

    private var nameUrl:String?=null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        val binding: FragmentAccountBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
          binding.accountViewModel=accountViewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val sharedPreferences:SharedPreferences = requireContext().getSharedPreferences("githubname", Context.MODE_PRIVATE)
        nameUrl=sharedPreferences.getString("githubUserName",null )
        getData(nameUrl.toString())

        signOut.setOnClickListener {
            val popupMenu= PopupMenu(context,signOut)

            popupMenu.menuInflater.inflate(R.menu.sign_out, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item!!.itemId) {
                    R.id.signOut -> {
                        Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
                        val firebaseUser = FirebaseAuth.getInstance()
                        firebaseUser.signOut()
                        val intent=Intent(activity,AuthenticationActivity::class.java)
                        activity?.startActivity(intent)
                        activity?.finish()
                    }
                }
                true
            }
            popupMenu.show()

        }

    }

    private fun getData(userName:String){
        accountViewModel.getUserData(userName).observe(viewLifecycleOwner, {
            if (it != null) {
                githubusername.text= it.name
                githubusernamebutton.text="github.com/${it.login}"
                username.text=it.login

                Glide.with(this)
                        .load(it.avatarUrl)
                        .placeholder(R.drawable.ic_baseline_account_circle_24)
                        .apply(RequestOptions().circleCrop())
                        .into(user_image_header)

                bio.text=it.bio
                followCount.text=it.followingCount.toString()
                followersCount.text=it.followersCount.toString()
                publicRepos.text=it.publicRepos.toString()
                myLocation.text=it.location

                Toast.makeText(context,it.login+"",Toast.LENGTH_SHORT).show()
                }

        });
        accountViewModel.makeApiCall()
    }
}