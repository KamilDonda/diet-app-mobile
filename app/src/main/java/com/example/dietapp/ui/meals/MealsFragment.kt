package com.example.dietapp.ui.meals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dietapp.R
import com.example.dietapp.adapters.MealsAdapter
import com.example.dietapp.models.Meal
import kotlinx.android.synthetic.main.fragment_meals.*

class MealsFragment : Fragment() {
    private val mealsAdapter = MealsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        meals_rv.adapter = mealsAdapter

        val meals = ArrayList<Meal>()
        meals.add(Meal(1, "Name1", "", "", 100f, 10f, 20.5f, 30.536f))
        meals.add(Meal(2, "Name2", "", "", 150.21f, 1f, 1f, 1f))
        meals.add(Meal(3, "Name3", "", "", 200.567f, 1f, 1f, 1f))
        meals.add(Meal(4, "Name4", "", "", 32.5f, 1f, 1f, 1f))
        meals.add(Meal(5, "Name5", "", "", 10f, 1f, 1f, 1f))
        meals.add(Meal(6, "Name6", "", "", 15f, 1f, 1f, 1f))

        mealsAdapter.setList(meals)
    }
}