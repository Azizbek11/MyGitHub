package com.azizbek.mygithub.ui.popular

import android.app.Service
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azizbek.mygithub.R
import com.azizbek.mygithub.adapter.UserRepoAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.my_lodaer_item.*

class PopularRepoFragment : Fragment() {

    var connectivity : ConnectivityManager? = null
    var info : NetworkInfo? = null
    var isOnline=false

    private var avatarUrl: String?=null

    private lateinit var homeviewmodel:popularRepoViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeviewmodel = ViewModelProvider(this).get(popularRepoViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

     checkNet()

    }

    private fun checkNet() {

        connectivity = context?.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager

        if ( connectivity != null) {
            info = connectivity!!.activeNetworkInfo

            if (info != null) {
                if (info!!.state == NetworkInfo.State.CONNECTED) {
                    isOnline = true
                }
            } else {
                pushDialog()
            }


            val bundle = activity?.intent?.extras
            if (bundle != null) {
                avatarUrl = bundle.getString("photoUrl")
            }

        }
        if (isOnline){
            isMain()
        }
    }

    private fun isMain() {

        dotsLoader.visibility = View.VISIBLE
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

    private fun pushDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.if_internet_net)
        builder.setCancelable(false)
        builder.setIcon(R.drawable.ic_baseline_network_check_24)
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
           checkNet()
        }

        builder.setNeutralButton(android.R.string.cancel) { dialog, which ->
            activity?.finish()
        }


        builder.show()
    }

    private fun getData(language:String){
        dotsLoader.visibility=View.VISIBLE
        home_recyclerView.visibility=View.GONE
        homeviewmodel.getRepo(language,"stars").observe(viewLifecycleOwner, {
            if (it != null) {
                home_recyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = UserRepoAdapter(context, it.items)
                    dotsLoader.visibility=View.GONE
                    home_recyclerView.visibility=View.VISIBLE
                }
            }
        });

    }

}