package com.example.dietapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.google.android.material.textview.MaterialTextView

class IngredientsOfMealAdapter : RecyclerView.Adapter<IngredientsOfMealAdapter.Holder>() {
    inner class Holder(view: View) : RecyclerView.ViewHolder(view)

    private val _list = ArrayList<String>()

    fun setList(list: ArrayList<String>) {
        _list.clear()
        _list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = _list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.ingredient_item, parent, false
        )
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val value = holder.itemView.findViewById<MaterialTextView>(R.id.ingredient_item_value)
        value.text = _list[position]
    }
}
