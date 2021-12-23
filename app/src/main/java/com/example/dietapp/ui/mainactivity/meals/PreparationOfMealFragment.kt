package com.example.dietapp.ui.mainactivity.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import kotlinx.android.synthetic.main.fragment_preparation_of_meal.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PreparationOfMealFragment : Fragment() {

    private val viewModel: MealViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_preparation_of_meal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparation_tv.text = viewModel.currentMeal!!.description
    }
}