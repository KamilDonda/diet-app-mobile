package com.example.dietapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.models.Meal
import com.example.dietapp.ui.mainactivity.meals.MealViewModel
import com.example.dietapp.utils.FloatConverter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class MealsAdapter(private val viewModel: MealViewModel) :
    RecyclerView.Adapter<MealsAdapter.Holder>() {
    inner class Holder(view: View) : RecyclerView.ViewHolder(view)

    private val _list = ArrayList<Meal>()

    fun setList(list: ArrayList<Meal>) {
        _list.clear()
        _list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = _list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_meal, parent, false
        )
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val root = holder.itemView.rootView
        val number = holder.itemView.findViewById<MaterialButton>(R.id.meal_number)
        val name = holder.itemView.findViewById<MaterialTextView>(R.id.meal_name)
        val kcal = holder.itemView.findViewById<MaterialButton>(R.id.meal_kcal)
        val proteins = holder.itemView.findViewById<MaterialTextView>(R.id.meal_proteins)
        val carbs = holder.itemView.findViewById<MaterialTextView>(R.id.meal_carbs)
        val fats = holder.itemView.findViewById<MaterialTextView>(R.id.meal_fats)

        val item = _list[position]

        number.text = (position + 1).toString()
        name.text = item.name
        kcal.text = FloatConverter.floatToString(item.kcal, "kcal")
        proteins.text = FloatConverter.floatToString(item.proteins, "g")
        carbs.text = FloatConverter.floatToString(item.carbs, "g")
        fats.text = FloatConverter.floatToString(item.fats, "g")

        root.setOnClickListener {
            viewModel.setCurrentMeal(position)
            it.findNavController().navigate(R.id.action_mealsFragment_to_mealFragment)
        }
    }
}
