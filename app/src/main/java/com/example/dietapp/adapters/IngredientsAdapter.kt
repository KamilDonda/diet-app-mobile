package com.example.dietapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.database.models.ingredient.IngredientEntity
import com.example.dietapp.ui.mainactivity.ingredients.IngredientViewModel
import com.example.dietapp.utils.FloatConverter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class IngredientsAdapter(private val viewModel: IngredientViewModel) :
    RecyclerView.Adapter<IngredientsAdapter.Holder>() {
    inner class Holder(view: View) : RecyclerView.ViewHolder(view)

    private val _list = ArrayList<IngredientEntity>()

    fun setList(list: ArrayList<IngredientEntity>) {
        _list.clear()
        _list.addAll(list)
        notifyDataSetChanged()
    }

    fun getFirstAppearancePosition(letter: Char): Int {
        var char = letter
        while (char <= 'Z') {
            _list.forEach {
                if (it.name.startsWith(char, true))
                    return _list.indexOf(it)
            }
            ++char
        }
        return itemCount - 1
    }

    override fun getItemCount() = _list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item, parent, false
        )
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val root = holder.itemView.rootView
        val number = holder.itemView.findViewById<MaterialButton>(R.id.item_number)
        val name = holder.itemView.findViewById<MaterialTextView>(R.id.item_name)
        val kcal = holder.itemView.findViewById<MaterialButton>(R.id.item_kcal)
        val proteins = holder.itemView.findViewById<MaterialTextView>(R.id.item_proteins)
        val carbs = holder.itemView.findViewById<MaterialTextView>(R.id.item_carbs)
        val fats = holder.itemView.findViewById<MaterialTextView>(R.id.item_fats)

        val item = _list[position]
        val isChecked = viewModel.filter.isChecked

        number.text = (position + 1).toString()
        name.text = item.name
        kcal.text = FloatConverter.floatToString(item.kcal.toFloat(), "kcal", isChecked)
        proteins.text = FloatConverter.floatToString(item.proteins.toFloat(), "g", isChecked)
        carbs.text = FloatConverter.floatToString(item.carbohydrates.toFloat(), "g", isChecked)
        fats.text = FloatConverter.floatToString(item.fats.toFloat(), "g", isChecked)

        root.setOnClickListener {
            viewModel.setCurrentIngredient(position)
//            it.findNavController().navigate(R.id.)
        }
    }
}
