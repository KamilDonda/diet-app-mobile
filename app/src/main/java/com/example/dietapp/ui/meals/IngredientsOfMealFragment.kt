package com.example.dietapp.ui.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.adapters.IngredientsAdapter
import com.example.dietapp.adapters.MacronutrientsAdapter
import com.example.dietapp.models.Meal
import kotlinx.android.synthetic.main.fragment_ingredients_of_meal.*
import org.eazegraph.lib.models.PieModel

class IngredientsOfMealFragment : Fragment() {

    private val macronutrientsAdapter = MacronutrientsAdapter()
    private val ingredientsAdapter = IngredientsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredients_of_meal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        macronutrients_rv.adapter = macronutrientsAdapter
        ingredients_rv.adapter = ingredientsAdapter

        val meal = Meal(
            1,
            "Name1",
            "",
            "",
            3200f,
            30f,
            64.5f,
            42.536f,
            arrayListOf(
                "600 g mielonej łopatki wieprzowej",
                "5 pieczarek",
                "3 ząbki czosnku",
                "łyżeczka słodkiej papryki"
            )
        )

        setMacronutrientsData(meal)
    }

    private fun setMacronutrientsData(meal: Meal) {
        val macronutrients = arrayListOf(
            Triple(
                ContextCompat.getColor(requireContext(), R.color.colorProteins),
                getString(R.string.proteins),
                meal.proteins
            ),
            Triple(
                ContextCompat.getColor(requireContext(), R.color.colorCarbs),
                getString(R.string.carbs),
                meal.carbs
            ),
            Triple(
                ContextCompat.getColor(requireContext(), R.color.colorFats),
                getString(R.string.fats),
                meal.fats
            )
        )
        macronutrientsAdapter.setList(macronutrients)
        ingredientsAdapter.setList(meal.ingredients)

        macronutrients.forEach {
            macronutrients_pieChart.addPieSlice(PieModel(it.third, it.first))
        }

        macronutrients_pieChart.startAnimation()
    }
}