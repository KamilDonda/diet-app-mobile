package com.example.dietapp.ui.mainactivity.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import kotlinx.android.synthetic.main.fragment_ingredient.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class IngredientFragment : Fragment() {

    private val viewModel: IngredientViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        one_ingredient_name.text = viewModel.currentIngredient?.name

        one_ingredient_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_ingredientFragment_to_ingredientsFragment)
        }
    }
}