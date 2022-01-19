package com.example.dietapp.ui.mainactivity.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.dietapp.R
import com.example.dietapp.adapters.AppPagerAdapter
import com.example.dietapp.services.LogoutService
import com.example.dietapp.utils.SlideAnimation
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.net.URL


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

        val photo = FirebaseAuth.getInstance().currentUser?.photoUrl.toString()
        setBitmapFromURL(photo)

        profile_name_area.doOnLayout { it ->
            val slide = SlideAnimation(it.measuredHeight, it)
            viewModel.hasInputFocus.observe(viewLifecycleOwner, {
                if (it != null) {
                    if (it) {
                        slide.start()
                    } else {
                        slide.end()
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

    private fun setBitmapFromURL(src: String?) {
        CoroutineScope(Job() + Dispatchers.IO).launch {
            try {
                val url = URL(src)
                val bitMap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                profile_iv.setImageBitmap(Bitmap.createScaledBitmap(bitMap, 100, 100, true))
            } catch (e: Exception) {
                Log.v(TAG, e.stackTraceToString())
            }
        }
    }

    companion object {
        const val TAG = "BITMAP"
    }
}