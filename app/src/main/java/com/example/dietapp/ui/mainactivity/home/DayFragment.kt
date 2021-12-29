package com.example.dietapp.ui.mainactivity.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import com.example.dietapp.adapters.StaticDayAdapter
import com.example.dietapp.models.Static
import kotlinx.android.synthetic.main.fragment_day.*
import org.eazegraph.lib.models.PieModel

class DayFragment : Fragment() {

    private val statisticsAdapter = StaticDayAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calories_rv.adapter = statisticsAdapter

        setMacronutrientsData(Static(1, 840f, 1280f, 640f))

        day_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_dayFragment_to_homeFragment)
        }
    }

    private fun setMacronutrientsData(static: Static) {
        val statistics = arrayListOf(
            Triple(
                ContextCompat.getColor(requireContext(), R.color.colorProteins),
                getString(R.string.breakfast),
                static.breakfast
            ),
            Triple(
                ContextCompat.getColor(requireContext(), R.color.colorCarbs),
                getString(R.string.lunch),
                static.lunch
            ),
            Triple(
                ContextCompat.getColor(requireContext(), R.color.colorFats),
                getString(R.string.dinner),
                static.dinner
            )
        )
        statisticsAdapter.setList(statistics)

        statistics.forEach {
            calories_pieChart.addPieSlice(PieModel(it.third, it.first))
        }

        calories_pieChart.startAnimation()
    }
}