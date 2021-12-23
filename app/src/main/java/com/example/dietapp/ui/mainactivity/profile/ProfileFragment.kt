package com.example.dietapp.ui.mainactivity.profile

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.dietapp.R
import com.example.dietapp.adapters.AppPagerAdapter
import com.example.dietapp.services.LogoutService
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ProfileFragment : Fragment(), TabLayout.OnTabSelectedListener {

    private val viewModel: ProfileViewModel by sharedViewModel()
    private val logoutService: LogoutService by inject()
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

        profile_name_area.doOnLayout { it ->
            var currentProgress = 0
            val maxHeight = it.measuredHeight
//            val slideDown = ValueAnimator.ofInt(currentProgress, maxHeight).apply {
//                duration = 1L
//                addUpdateListener { animation ->
//                    currentProgress = (animation.animatedValue as Int)
//                    profile_name_area.layoutParams.height = currentProgress
//                    profile_name_area.requestLayout()
//                }
//                doOnStart {
//                    profile_name_area.layoutParams.height = 0
//                    profile_name_area.visibility = View.VISIBLE
//                }
//            }
            val slideUp = ValueAnimator.ofInt(currentProgress, maxHeight).apply {
                duration = 300L
                addUpdateListener { animation ->
                    currentProgress = (animation.animatedValue as Int)
                    profile_name_area.layoutParams.height = maxHeight - currentProgress
                    profile_name_area.requestLayout()
                }
                doOnEnd {
                    profile_name_area.visibility = View.GONE
                }
            }
            viewModel.hasInputFocus.observe(viewLifecycleOwner, {
                if (it != null) {
                    if (it) {
                        slideUp.start()
                    } else {
                        slideUp.end()
                        profile_name_area.visibility = View.VISIBLE
                    }
                }
            })
        }

        logout_button.setOnClickListener {
            logoutService.logout(activity as AppCompatActivity)
        }
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
    override fun onTabReselected(tab: TabLayout.Tab?) {
        profile_root.requestFocus()
    }
}