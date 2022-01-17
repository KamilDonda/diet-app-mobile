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

        val homeMealAdapter = HomeMealAdapter()
        home_rv.adapter = homeMealAdapter

        setupButtons()

        viewModel.meals.observe(viewLifecycleOwner, {
            homeMealAdapter.setList(it)
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
            viewModel.generateDiet()
        }
    }
}