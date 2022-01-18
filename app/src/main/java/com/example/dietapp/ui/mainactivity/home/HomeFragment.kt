package com.example.dietapp.ui.mainactivity.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import com.example.dietapp.adapters.HomeMealAdapter
import com.example.dietapp.utils.ArrayUtil.Companion.getArrayList
import com.example.dietapp.utils.DateUtil
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by sharedViewModel()

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

        val homeMealAdapter = HomeMealAdapter()
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
            viewModel.generateDiet().observe(viewLifecycleOwner, {
                viewModel.setDietOfWeek()
            })
        }
    }
}