package com.example.dietapp.ui.mainactivity.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import com.example.dietapp.adapters.HomeMealAdapter
import com.example.dietapp.database.models.diet.DietEntity
import com.example.dietapp.services.FirebaseService
import com.example.dietapp.sharedpreferences.Preferences
import com.example.dietapp.ui.mainactivity.SharedViewModel
import com.example.dietapp.utils.ArrayUtil.Companion.getArrayList
import com.example.dietapp.utils.DateUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by sharedViewModel()
    private val sharedViewModel: SharedViewModel by sharedViewModel()
    private val sharedPreferences: Preferences by inject()
    private val firebaseService: FirebaseService by inject()
    private lateinit var homeMealAdapter: HomeMealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setDietOfWeek()

        homeMealAdapter = HomeMealAdapter(viewModel, sharedViewModel)
        home_rv.adapter = homeMealAdapter

        setupButtons()

        viewModel.dietOfWeek.observe(viewLifecycleOwner, { dietList ->
            if (dietList.isNotEmpty()) {

                val currentDate = DateUtil.getCurrentDay()
                val currentDiet =
                    dietList.find { (DateUtil.difference(currentDate, it.date) % 7) == 0L }

                viewModel.setCurrentDiet(currentDiet!!)
                viewModel.setHomeMeals()
            }
        })

        viewModel.homeMeals.observe(viewLifecycleOwner, {
            homeMealAdapter.setList(it)

            if (viewModel.currentDiet != null) {
                val date = DateUtil.longToDate(viewModel.currentDiet!!.date)
                home_day.text = getArrayList(R.array.days_of_week, requireContext())[date.day]
                welcome_text.visibility = View.GONE
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {}
            })
    }

    private fun setupButtons() {
        day_stats.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_dayFragment)
        }
        week_stats.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_weekFragment)
        }
        generate_diet.setOnClickListener {
            val user = sharedPreferences.getProfileData()
            val canGenerate = user.canGenerate()
            when {
                canGenerate == null -> {
                    Snackbar.make(
                        requireView(),
                        "Minimalny wiek wynosi 17 lat!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                canGenerate -> {
                    viewModel.generateDiet().observe(viewLifecycleOwner, {
                        viewModel.setDietOfWeek()
                    })
                }
                else -> {
                    Snackbar.make(
                        requireView(),
                        "Nie wszystkie dane są uzupełnione poprawnie!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        edit_button.setOnClickListener {
            changeEditMode()
        }

        save_button.visibility = if (sharedViewModel.isEditModeOn) View.VISIBLE else View.GONE
        save_button.setOnClickListener {
            if (sharedViewModel.isEditModeOn) {
                val ids = sharedViewModel.newMeals.getIds()
                val b = if (ids[0] == -1) viewModel.currentDiet!!.breakfast.id else ids[0]
                val d = if (ids[1] == -1) viewModel.currentDiet!!.dinner.id else ids[1]
                val s = if (ids[2] == -1) viewModel.currentDiet!!.supper.id else ids[2]

                val diet = DietEntity(
                    viewModel.currentDiet!!.id,
                    breakfast = b,
                    dinner = d,
                    supper = s,
                    date = viewModel.currentDiet!!.date
                )

                val user = sharedPreferences.getProfileData()
                val index = viewModel.dietOfWeek.value!!.indexOfFirst { it.id == diet.id }

                user.diet[index] = diet
                sharedPreferences.setProfileData(user)
                firebaseService.updateUserDiet(user.uid, user.diet)
                viewModel.setDietOfWeek()

                changeEditMode()
            }
        }

        changeClickableButtons()
    }

    private fun changeClickableButtons() {
        listOf(day_stats, week_stats, generate_diet).forEach {
            it.isClickable = !sharedViewModel.isEditModeOn
        }
    }

    private fun changeEditMode() {
        sharedViewModel.changeEditMode()
        changeClickableButtons()

        save_button.visibility = if (sharedViewModel.isEditModeOn) View.VISIBLE else View.GONE

        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            ?.menu?.forEach {
                it.isEnabled = !sharedViewModel.isEditModeOn
            }

        homeMealAdapter.notifyDataSetChanged()
    }
}