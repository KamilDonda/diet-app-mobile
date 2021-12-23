package com.example.dietapp

import com.example.dietapp.ui.mainactivity.meals.MealViewModel
import com.example.dietapp.ui.mainactivity.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val appModule = module {

    viewModel { MealViewModel() }
    viewModel { ProfileViewModel() }
}