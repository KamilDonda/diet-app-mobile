package com.example.dietapp.ui.mainactivity.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_day.*
import kotlinx.android.synthetic.main.fragment_week.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.DAYS


class WeekFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_week, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Calories()

        week_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_weekFragment_to_homeFragment)
        }
    }

    private fun Calories()
    {
        val days = ArrayList<BarEntry>()
        val barChart = idbarchart

        val x = arrayOf(
            "Pn",
            "Pn",
            "Wt",
            "Śr",
            "Czw",
            "Pt",
            "Sb",
            "Ndz"
        )

        days.add(BarEntry(1f, 2050f))
        days.add(BarEntry(2f, 2740f))
        days.add(BarEntry(3f, 2009f))
        days.add(BarEntry(4f, 2150f))
        days.add(BarEntry(5f, 2510f))
        days.add(BarEntry(6f, 2400f))
        days.add(BarEntry(7f, 2030f))

        val barDataSet = BarDataSet(days, "Kalorie")
        barDataSet.setColors(resources.getColor(R.color.colorCarbs))
        barDataSet.setDrawValues(true)
        barDataSet.valueTextSize = 12f
        barChart.description.isEnabled = false

        val xAxis = barChart.xAxis
        val legend = barChart.legend

        val data = BarData(barDataSet)
        barChart.data = data
        barChart.setVisibleXRangeMaximum(7f)
        data.barWidth = 0.45f

        legend.isEnabled = true
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.textSize = 12f
        legend.xEntrySpace = 22f
//        legend.xOffset = 14f
//        legend.yOffset = 12f

        xAxis.position = XAxis.XAxisPosition.TOP
        xAxis.setDrawGridLines(true)
        xAxis.setDrawAxisLine(true)
        //xAxis.setCenterAxisLabels(true)
        xAxis.valueFormatter = IndexAxisValueFormatter(x)
//        xAxis.axisMinimum = 0f
//        xAxis.granularity = 1.12f  //Przesuwanie opisu osi X (śniadanie, obiad, kolacja)
        xAxis.textSize = 12f
//        xAxis.axisMaximum = data.xMax + 0.6f
        xAxis.yOffset = 1.2f


        barChart.animateY(1500)
        barChart.invalidate()
    }
}