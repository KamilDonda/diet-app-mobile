package com.example.dietapp.ui.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.dietapp.R
import com.example.dietapp.adapters.MealPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_meal.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MealFragment : Fragment(), TabLayout.OnTabSelectedListener {

    private val viewModel: MealViewModel by sharedViewModel()
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

        one_meal_name.text = viewModel.currentMeal?.name

        setupTab(view)

        one_meal_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_mealFragment_to_mealsFragment)
        }
    }

    private fun setupTab(view: View) {
        tabLayout = view.findViewById(R.id.tabLayout)
        tabLayout.addOnTabSelectedListener(this)

        val manager = childFragmentManager
        val adapter = MealPagerAdapter(manager, lifecycle)

        viewPager2 = view.findViewById(R.id.viewPager2)
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