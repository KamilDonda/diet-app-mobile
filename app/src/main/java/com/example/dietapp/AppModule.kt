package com.example.dietapp

import com.example.dietapp.database.repositories.IngredientRepo
import com.example.dietapp.database.repositories.MealRepo
import com.example.dietapp.database.retrofit.RetrofitBuilder
import com.example.dietapp.services.*
import com.example.dietapp.sharedpreferences.Preferences
import com.example.dietapp.ui.loginactivity.login.LoginFragment
import com.example.dietapp.ui.loginactivity.login.LoginViewModel
import com.example.dietapp.ui.loginactivity.register.RegisterFragment
import com.example.dietapp.ui.loginactivity.register.RegisterViewModel
import com.example.dietapp.ui.loginactivity.start.StartFragment
import com.example.dietapp.ui.mainactivity.SharedViewModel
import com.example.dietapp.ui.mainactivity.home.DayFragment
import com.example.dietapp.ui.mainactivity.home.HomeFragment
import com.example.dietapp.ui.mainactivity.home.HomeViewModel
import com.example.dietapp.ui.mainactivity.home.WeekFragment
import com.example.dietapp.ui.mainactivity.ingredients.IngredientFragment
import com.example.dietapp.ui.mainactivity.ingredients.IngredientViewModel
import com.example.dietapp.ui.mainactivity.ingredients.IngredientsFragment
import com.example.dietapp.ui.mainactivity.meals.*
import com.example.dietapp.ui.mainactivity.profile.ProfileAccountFragment
import com.example.dietapp.ui.mainactivity.profile.ProfileDataFragment
import com.example.dietapp.ui.mainactivity.profile.ProfileFragment
import com.example.dietapp.ui.mainactivity.profile.ProfileViewModel
import com.example.dietapp.utils.AgeConverter
import com.example.dietapp.utils.FloatConverter
import com.example.dietapp.utils.HideKeyboard
import com.example.dietapp.utils.PasswordUtil
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val appModule = module {

// Retrofit
    single { RetrofitBuilder }

// ViewModel
    viewModel { MealViewModel(get()) }
    viewModel { IngredientViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SharedViewModel() }

// Services
    single { FirebaseService() }
    single { ConnectionService(get(), get(), get()) }
    single { LoginService(get(), get()) }
    single { LogoutService(get()) }
    single { DatabaseService(get()) }

// Repositories
    single { IngredientRepo(get()) }
    single { MealRepo(get(), get()) }

// SharedPreferences
    single { Preferences(get()) }

// Utils
    single { AgeConverter() }
    single { FloatConverter() }
    single { HideKeyboard }
    single { PasswordUtil }

//Fragments
    // Start
    single { StartFragment() }
    // Login
    single { LoginFragment() }
    // Register
    single { RegisterFragment() }

    // Home
    single { HomeFragment() }
    single { DayFragment() }
    single { WeekFragment() }
    // Ingredients
    single { IngredientsFragment() }
    single { IngredientFragment() }
    // Meals
    single { MealsFragment() }
    single { MealFragment() }
    single { IngredientsOfMealFragment() }
    single { PreparationOfMealFragment() }
    // Profile
    single { ProfileFragment() }
    single { ProfileDataFragment() }
    single { ProfileAccountFragment() }
}
