package com.azizbek.mygithub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azizbek.mygithub.R
import com.azizbek.mygithub.adapter.UserRepoAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var avatarUrl: String?=null

    private lateinit var homeviewmodel:HomeViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeviewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val bundle= activity?.intent?.extras
        if (bundle != null) {
            avatarUrl = bundle.getString("photoUrl")
        }

        homeviewmodel.responseHaveData().observe(viewLifecycleOwner, {
            dotsLoader.visibility = it
            if (it == View.GONE) {
                home_recyclerView.visibility=View.VISIBLE
            }
        })

        getData("kotlin")

        Glide.with(this)
            .load(avatarUrl)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .apply(RequestOptions().circleCrop())
            .into(account_image)

        image_filter.setOnClickListener {
            val popupMenu=PopupMenu(context,image_filter)

            popupMenu.menuInflater.inflate(R.menu.popup, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item!!.itemId) {
                    R.id.kotlin -> getData("kotlin")
                    R.id.java -> getData("java")
                    R.id.php -> getData("php")
                    R.id.python -> getData("python")
                    R.id.javascript -> getData("javascript")
                    R.id.swift -> getData("swift")
                }
                true
            }
            popupMenu.show()

        }
    }

    private fun getData(language:String){
        homeviewmodel.getRecyclerListData("language:$language","stars").observe(viewLifecycleOwner, {
            if (it != null) {
                home_recyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = UserRepoAdapter(context, it.items)
                }
            }
        });
        homeviewmodel.makeApiCall()
    }
}