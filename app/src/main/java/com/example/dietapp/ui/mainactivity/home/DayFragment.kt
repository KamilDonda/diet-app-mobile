package com.example.dietapp.ui.mainactivity.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import com.example.dietapp.adapters.MacroDayAdapter
import com.example.dietapp.adapters.StaticDayAdapter
import com.example.dietapp.models.Macroday
import com.example.dietapp.models.Static
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.fragment_day.*
import org.eazegraph.lib.models.PieModel
import java.util.*


class DayFragment : Fragment() {

    private val statisticsAdapter = StaticDayAdapter()
    private val macrodayAdapter = MacroDayAdapter()

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
        macro_rv.adapter = macrodayAdapter

        setStatisticsData(Static(1, 840f, 1280f, 640f))
        setMacroData(Macroday(1, 245f, 124f, 368f))
        lol()

        day_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_dayFragment_to_homeFragment)
        }

    }

    private fun setStatisticsData(static: Static) {
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

    private fun setMacroData(macroDay: Macroday) {
        val macrosday = arrayListOf(
            Triple(
                ContextCompat.getColor(requireContext(), R.color.colorProteins),
                getString(R.string.proteins),
                macroDay.proteins
            ),
            Triple(
                ContextCompat.getColor(requireContext(), R.color.colorCarbs),
                getString(R.string.carbs),
                macroDay.carbs
            ),
            Triple(
                ContextCompat.getColor(requireContext(), R.color.colorFats),
                getString(R.string.fats),
                macroDay.fats
            )
        )
        macrodayAdapter.setList(macrosday)

        macrosday.forEach {
            macro_pieChart.addPieSlice(PieModel(it.third, it.first))
        }

        macro_pieChart.startAnimation()
    }

    private fun getBarProteins(): ArrayList<BarEntry> {

        val barEntries = ArrayList<BarEntry>()

        barEntries.add(BarEntry(1f, 14.5f))
        barEntries.add(BarEntry(2f, 12.5f))
        barEntries.add(BarEntry(3f, 6.2f))
        return barEntries
    }

    private fun getBarCarbs(): ArrayList<BarEntry> {

        val barEntries = ArrayList<BarEntry>()

        barEntries.add(BarEntry(1f, 8.5f))
        barEntries.add(BarEntry(2f, 4.2f))
        barEntries.add(BarEntry(3f, 7.8f))
        return barEntries
    }

    private fun getBarFats(): ArrayList<BarEntry> {

        val barEntries = ArrayList<BarEntry>()

        barEntries.add(BarEntry(1f, 6.8f))
        barEntries.add(BarEntry(2f, 11.1f))
        barEntries.add(BarEntry(3f, 7.2f))
        return barEntries
    }

    private fun lol()
    {
        //Tworzenie zmiennych do wykresu
        val barChart = idBarChart
        val days = arrayOf(
            getString(R.string.breakfast),
            getString(R.string.lunch),
            getString(R.string.dinner)
        )

        //Inicjacja danych, kolorów, wartości nad wykresami
        val barDataSet1 = BarDataSet(getBarProteins(), getString(R.string.proteins))
        barDataSet1.setColors(resources.getColor(R.color.colorProteins))
        barDataSet1.setDrawValues(true)
        barDataSet1.valueTextSize = 12f
        val barDataSet2 = BarDataSet(getBarCarbs(), getString(R.string.carbs))
        barDataSet2.setColors(resources.getColor(R.color.colorCarbs))
        barDataSet2.setDrawValues(true)
        barDataSet2.valueTextSize = 12f
        val barDataSet3 = BarDataSet(getBarFats(), getString(R.string.fats))
        barDataSet3.setColors(resources.getColor(R.color.colorFats))
        barDataSet3.setDrawValues(true)
        barDataSet3.valueTextSize = 12f

        //Stworzenie zmiennej data ze wszystkimi danymi do wykresu
        val data = BarData(barDataSet1, barDataSet2, barDataSet3)

        //Stworzenie zmiennej dla opisu osi X i legendy
        val xAxis = barChart.xAxis
        val legend = barChart.legend

        barChart.data = data
        barChart.description.isEnabled = false
        barChart.isDragEnabled = true
        barChart.setVisibleXRangeMaximum(3f)
//        barChart.setDoubleTapToZoomEnabled(false)
//        barChart.setPinchZoom(false)
//        barChart.setScaleEnabled(false)

        legend.isEnabled = true
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.textSize = 14f
        legend.xEntrySpace = 22f
        legend.xOffset = 14f
        legend.yOffset = 12f

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.setDrawAxisLine(true)
        xAxis.setCenterAxisLabels(true)
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1.245f  //Przesuwanie opisu osi X (śniadanie, obiad, kolacja)
        xAxis.textSize = 14f
        xAxis.axisMaximum = data.xMax + 0.74f
        xAxis.valueFormatter = IndexAxisValueFormatter(days)
        xAxis.isGranularityEnabled = true
        xAxis.yOffset = -1.2f

        val barSpace = 0.2f
        val groupSpace = 0.12f
        data.barWidth = 0.18f

        barChart.groupBars(0f, groupSpace, barSpace)

        barChart.animateXY(2000, 2000)
        barChart.invalidate()
    }
}