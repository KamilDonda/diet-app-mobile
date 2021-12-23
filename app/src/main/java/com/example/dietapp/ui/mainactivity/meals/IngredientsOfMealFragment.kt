package com.example.dietapp.ui.mainactivity.meals

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
import com.example.dietapp.utils.FloatConverter
import kotlinx.android.synthetic.main.fragment_ingredients_of_meal.*
import org.eazegraph.lib.models.PieModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class IngredientsOfMealFragment : Fragment() {

    private val viewModel: MealViewModel by sharedViewModel()
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

        setMacronutrientsData(viewModel.currentMeal!!)
        setIngredientsData(viewModel.currentMeal!!)
        macronutrient_kcal_value.text =
            FloatConverter.floatToString(
                viewModel.currentMeal!!.kcal,
                "kcal"
            )
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

        macronutrients.forEach {
            macronutrients_pieChart.addPieSlice(PieModel(it.third, it.first))
        }

        macronutrients_pieChart.startAnimation()
    }

    private fun setIngredientsData(meal: Meal) {
        ingredientsAdapter.setList(meal.ingredients)
    }
}