package com.azizbek.mygithub.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azizbek.mygithub.R
import com.azizbek.mygithub.adapter.UserRepoAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.my_lodaer_item.*

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        suggest_query.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(suggest_query.text.isNotEmpty()){
                getData(suggest_query.text.toString())
                 }
            }
            true
        }

        clear.setOnClickListener {
            suggest_query.text=null
        }

        searchViewModel.responseHaveData().observe(viewLifecycleOwner, {
            dotsLoader.visibility=it
            if (it == View.GONE) {
                scroll_view.visibility=View.VISIBLE
            }
        })
    }

    private fun getData(language:String){
        dotsLoader.visibility=View.VISIBLE
        scroll_view.visibility=View.INVISIBLE
        searchViewModel.getRecyclerListData(language,"stars").observe(viewLifecycleOwner, {
            if (it != null) {
                search_recyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = UserRepoAdapter(context, it.items)
                }
            }
        });
        searchViewModel.makeApiCall()
    }
}