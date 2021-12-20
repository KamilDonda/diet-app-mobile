package com.example.dietapp

import com.example.dietapp.ui.meals.MealViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val appModule = module {

    viewModel { MealViewModel() }
}