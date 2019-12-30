package com.example.ringz.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.example.ringz.R
import com.example.ringz.adapters.HouseListAdapter
import com.example.ringz.models.Home
import com.example.ringz.models.User
import kotlinx.android.synthetic.main.fragment_homevisitor_list.*

class HomeVisitorListFragment : Fragment(), View.OnClickListener, AdapterView.OnItemClickListener {
    private lateinit var mainActivity : MainActivity
    private var homesList: List<Home> = emptyList()
    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val  view = inflater.inflate(R.layout.fragment_homevisitor_list, container, false)
        mainActivity = this.activity as MainActivity
        user= mainActivity.user!!

        if (user.houseList.isNotEmpty()) {
            homesList = user.houseList
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (user.houseList.isNotEmpty()) {

            val listNames = user.houseList.map { home -> home.name }
            val listHouses = user.houseList.map { home -> home.uuid }

            val adapter = HouseListAdapter(
                mainActivity,
                listNames,
                listHouses,
                user.houseList
            )

            houses_list.adapter = adapter
            houses_list.setOnItemClickListener(this)
        }



    }

    override fun onStart() {

        add_visitHouse_button.setOnClickListener(this)

        super.onStart()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.add_visitHouse_button -> onAddClick()
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mainActivity.renderFragment(HomeVisitorFragment())
    }

    private fun onAddClick() {
        mainActivity.renderFragment(AddVisitingHomeFragment())
    }
}