package com.example.dietapp.ui.mainactivity.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import com.example.dietapp.adapters.MacronutrientsAdapter
import com.example.dietapp.database.models.ingredient.IngredientEntity
import com.example.dietapp.utils.FloatConverter.Companion.floatToString
import kotlinx.android.synthetic.main.fragment_ingredient.*
import org.eazegraph.lib.models.PieModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class IngredientFragment : Fragment() {

    private val viewModel: IngredientViewModel by sharedViewModel()
    private val macronutrientsAdapter = MacronutrientsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        macronutrients_rv.adapter = macronutrientsAdapter
        setMacronutrientsData(viewModel.currentIngredient!!)

        one_ingredient_name.text = viewModel.currentIngredient!!.name
        macronutrients_kcal.text =
            floatToString(viewModel.currentIngredient!!.kcal, "kcal", "Kaloryczność")

        one_ingredient_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_ingredientFragment_to_ingredientsFragment)
        }
    }

    private fun setMacronutrientsData(ingredient: IngredientEntity) {
        val macronutrients = arrayListOf(
            Triple(
                ContextCompat.getColor(requireContext(), R.color.colorProteins),
                getString(R.string.proteins),
                ingredient.proteins
            ),
            Triple(
                ContextCompat.getColor(requireContext(), R.color.colorCarbs),
                getString(R.string.carbs),
                ingredient.carbohydrates
            ),
            Triple(
                ContextCompat.getColor(requireContext(), R.color.colorFats),
                getString(R.string.fats),
                ingredient.fats
            )
        )
        macronutrientsAdapter.setList(macronutrients)

        macronutrients.forEach {
            macronutrients_pieChart.addPieSlice(PieModel(it.third, it.first))
        }

        macronutrients_pieChart.startAnimation()
    }
}