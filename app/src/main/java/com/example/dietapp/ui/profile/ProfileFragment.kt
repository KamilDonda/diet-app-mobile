package com.example.dietapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.dietapp.R
import com.example.dietapp.adapters.AppPagerAdapter
import com.google.android.material.tabs.TabLayout

class ProfileFragment : Fragment(), TabLayout.OnTabSelectedListener {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTab(view)
    }

    private fun setupTab(view: View) {
        tabLayout = view.findViewById(R.id.tabLayout_profile)
        tabLayout.addOnTabSelectedListener(this)

        val manager = childFragmentManager
        val adapter = AppPagerAdapter(
            manager,
            lifecycle,
            arrayOf(ProfileDataFragment(), ProfileAccountFragment())
        )

        viewPager2 = view.findViewById(R.id.viewPager2_profile)
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