package com.treedevs.tahfidz.adapters
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.treedevs.tahfidz.models.surat

class suratAdapter(
    context: Context,
    items: List<surat>
    ) : ArrayAdapter<surat>(context, android.R.layout.simple_spinner_item, items) {
    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.text = getItem(position)?.nama
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.text = getItem(position)?.nama
        return view
    }
    }