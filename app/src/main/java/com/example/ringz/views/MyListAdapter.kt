package com.example.ringz.views

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ringz.R

class MyListAdapter(private val context: Activity, private val title: List<String>, private val description: List<Boolean>, private val homeList: List<String>)
    : ArrayAdapter<String>(context, R.layout.custom_list, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val subtitleText = rowView.findViewById(R.id.description) as TextView

        titleText.text = title[position]
        subtitleText.text = description[position].toString()

        val ringButton = rowView.findViewById(R.id.ring_button) as Button
        ringButton.setOnClickListener {
            Log.d("ringing","BZZZZZZZZZZZZZ")
            //ringBell(homeList[position])
        }

        return rowView
    }
}