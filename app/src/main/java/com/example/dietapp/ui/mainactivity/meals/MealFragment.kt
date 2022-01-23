package com.example.dietapp.ui.mainactivity.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.dietapp.R
import com.example.dietapp.adapters.AppPagerAdapter
import com.example.dietapp.ui.mainactivity.SharedViewModel
import com.example.dietapp.ui.mainactivity.home.HomeViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_meal.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MealFragment : Fragment(), TabLayout.OnTabSelectedListener {

    private val viewModel: MealViewModel by sharedViewModel()
    private val homeViewModel: HomeViewModel by sharedViewModel()
    private val sharedViewModel: SharedViewModel by sharedViewModel()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        change_button.visibility = if (sharedViewModel.isEditModeOn) View.VISIBLE else View.GONE

        change_button.setOnClickListener {
            if (sharedViewModel.isEditModeOn) {
                sharedViewModel.updateNewMeal(viewModel.currentMeal!!.id)
                it.findNavController().navigate(R.id.homeFragment)
            }
        }

        if (homeViewModel.currentMeal != null) {
            viewModel.setCurrentMeal(homeViewModel.currentMeal!!)
            homeViewModel.setCurrentMeal(null)
        }

        one_meal_name.text = viewModel.currentMeal?.name

        setupTab(view)

        one_meal_back_button.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun setupTab(view: View) {
        tabLayout = view.findViewById(R.id.tabLayout_one_meal)
        tabLayout.addOnTabSelectedListener(this)

        val manager = childFragmentManager
        val adapter = AppPagerAdapter(
            manager,
            lifecycle,
            arrayOf(IngredientsOfMealFragment(), PreparationOfMealFragment())
        )

        viewPager2 = view.findViewById(R.id.viewPager2_one_meal)
        viewPager2.adapter = adapter
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        viewPager2.currentItem = tab!!.position
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}
}