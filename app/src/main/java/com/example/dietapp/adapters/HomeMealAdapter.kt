package com.example.dietapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.models.MealHome
import com.example.dietapp.utils.FloatConverter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class HomeMealAdapter :
    RecyclerView.Adapter<HomeMealAdapter.Holder>() {
    inner class Holder(view: View) : RecyclerView.ViewHolder(view)

    private val _list = ArrayList<MealHome>()

    fun setList(list: ArrayList<MealHome>) {
        _list.clear()
        _list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = _list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.home_meal, parent, false
        )
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val name = holder.itemView.findViewById<MaterialTextView>(R.id.home_meal_name)
        val type = holder.itemView.findViewById<MaterialTextView>(R.id.home_meal_type)
        val kcal = holder.itemView.findViewById<MaterialButton>(R.id.home_meal_kcal)

        val item = _list[position]

        name.text = item.name
        type.text = item.type
        kcal.text = FloatConverter.floatToString(item.kcal, "kcal")
    }
}
