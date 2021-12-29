package com.example.dietapp.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.utils.FloatConverter
import com.google.android.material.textview.MaterialTextView

class MacroDayAdapter : RecyclerView.Adapter<MacroDayAdapter.Holder>() {
    inner class Holder(view: View) : RecyclerView.ViewHolder(view)

    private val _list = ArrayList<Triple<Int, String, Float>>()

    fun setList(list: ArrayList<Triple<Int, String, Float>>) {
        _list.clear()
        _list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = _list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.static_day_item, parent, false
        )
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dot = holder.itemView.findViewById<View>(R.id.static_day_item_dot)
        val name = holder.itemView.findViewById<MaterialTextView>(R.id.static_day_item_name)
        val value = holder.itemView.findViewById<MaterialTextView>(R.id.static_day_item_value)

        val item = _list[position]

        dot.backgroundTintList = ColorStateList.valueOf(item.first)
        name.text = item.second
        value.text = FloatConverter.floatToString(item.third, "g")
    }
}