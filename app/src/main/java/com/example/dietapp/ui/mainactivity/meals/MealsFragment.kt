package com.example.dietapp.ui.mainactivity.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.adapters.MealsAdapter
import kotlinx.android.synthetic.main.fragment_meals.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MealsFragment : Fragment() {
    private val viewModel: MealViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealsAdapter = MealsAdapter(viewModel)
        meals_rv.adapter = mealsAdapter

        viewModel.meals.observe(viewLifecycleOwner, {
            mealsAdapter.setList(it)
        })
    }
}