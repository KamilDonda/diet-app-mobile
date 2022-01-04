package com.example.dietapp.ui.mainactivity.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_week.*
import kotlin.collections.ArrayList


class WeekFragment : Fragment() {

    private lateinit var x: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_week, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        x = resources.getStringArray(R.array.days)
        calories()
        drawLineChart()
        drawLineChartMacro()

        week_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_weekFragment_to_homeFragment)
        }
    }

//    override fun onNothingSelected() {
//    }
//
//    override fun onValueSelected(e: Entry?, h: Highlight?) {
//        Log.d("LineChart", e?.y.toString())
//        Log.d("LineChart", (e as Entry).toString())
//    }

    private fun calories()
    {
        val days = ArrayList<BarEntry>()
        val barChart = idbarchart

        days.add(BarEntry(0f, 2050f))
        days.add(BarEntry(1f, 2740f))
        days.add(BarEntry(2f, 2009f))
        days.add(BarEntry(3f, 2150f))
        days.add(BarEntry(4f, 2510f))
        days.add(BarEntry(5f, 2400f))
        days.add(BarEntry(6f, 2030f))

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

        xAxis.position = XAxis.XAxisPosition.BOTH_SIDED
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

    private fun drawLineChart() {
        val lineChart =  idlinechart
        val lineEntries1 = getDataSet1()
        val lineEntries2 = getDataSet2()
        val lineEntries3 = getDataSet3()
        val lineDataSet1 = LineDataSet(lineEntries1, getString(R.string.breakfast))
        val lineDataSet2 = LineDataSet(lineEntries2, getString(R.string.lunch))
        val lineDataSet3 = LineDataSet(lineEntries3, getString(R.string.dinner))

        lineDataSet1.axisDependency = YAxis.AxisDependency.RIGHT
        lineDataSet1.isHighlightEnabled = true
        lineDataSet1.lineWidth = 2f
        lineDataSet1.setColors(resources.getColor(R.color.colorProteins))
        lineDataSet1.setCircleColor(resources.getColor(R.color.colorProteinsDark))
        lineDataSet1.circleRadius = 4f
        lineDataSet1.setDrawHighlightIndicators(true)
        lineDataSet1.highLightColor = Color.RED
        lineDataSet1.valueTextSize = 10f
        lineDataSet1.valueTextColor = Color.DKGRAY

        lineDataSet2.axisDependency = YAxis.AxisDependency.RIGHT
        lineDataSet2.isHighlightEnabled = true
        lineDataSet2.lineWidth = 2f
        lineDataSet2.setColors(resources.getColor(R.color.colorCarbs))
        lineDataSet2.setCircleColor(resources.getColor(R.color.colorCarbsDark))
        lineDataSet2.circleRadius = 4f
        lineDataSet2.setDrawHighlightIndicators(true)
        lineDataSet2.highLightColor = Color.RED
        lineDataSet2.valueTextSize = 10f
        lineDataSet2.valueTextColor = Color.DKGRAY

        lineDataSet3.axisDependency = YAxis.AxisDependency.RIGHT
        lineDataSet3.isHighlightEnabled = true
        lineDataSet3.lineWidth = 2f
        lineDataSet3.setColors(resources.getColor(R.color.colorFats))
        lineDataSet3.setCircleColor(resources.getColor(R.color.colorFatsDark))
        lineDataSet3.circleRadius = 4f
        lineDataSet3.setDrawHighlightIndicators(true)
        lineDataSet3.highLightColor = Color.RED
        lineDataSet3.valueTextSize = 10f
        lineDataSet3.valueTextColor = Color.DKGRAY

        val dataSets = arrayListOf(lineDataSet1, lineDataSet2, lineDataSet3)
        val lineData = LineData(dataSets as List<ILineDataSet>?)

        val legend = lineChart.legend
        legend.textSize = 12f
        legend.xEntrySpace = 16f

        lineChart.description.isEnabled = false
        lineChart.setDrawMarkers(true)
        //lineChart.setOnChartValueSelectedListener(this)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTH_SIDED
        lineChart.xAxis.isGranularityEnabled = true
        lineChart.xAxis.granularity = 1.0f
        lineChart.xAxis.textSize = 12f
        lineChart.xAxis.yOffset = 0.6f
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(x)
        lineChart.animateY(1500)
        lineChart.data = lineData
        lineChart.invalidate()
    }

    private fun getDataSet1() :  ArrayList<Entry>{
        val lineEntries = ArrayList<Entry>()
        lineEntries.add(Entry(0f, 645f))
        lineEntries.add(Entry(1f, 589f))
        lineEntries.add(Entry(2f, 602f))
        lineEntries.add(Entry(3f, 634f))
        lineEntries.add(Entry(4f, 588f))
        lineEntries.add(Entry(5f, 610f))
        lineEntries.add(Entry(6f, 624f))

        return lineEntries
    }

    private fun getDataSet2() :  ArrayList<Entry>{
        val lineEntries = ArrayList<Entry>()
        lineEntries.add(Entry(0f, 870f))
        lineEntries.add(Entry(1f, 790f))
        lineEntries.add(Entry(2f, 924f))
        lineEntries.add(Entry(3f, 901f))
        lineEntries.add(Entry(4f, 865f))
        lineEntries.add(Entry(5f, 845f))
        lineEntries.add(Entry(6f, 819f))

        return lineEntries
    }

    private fun getDataSet3() :  ArrayList<Entry>{
        val lineEntries = ArrayList<Entry>()
        lineEntries.add(Entry(0f, 645f))
        lineEntries.add(Entry(1f, 802f))
        lineEntries.add(Entry(2f, 780f))
        lineEntries.add(Entry(3f, 680f))
        lineEntries.add(Entry(4f, 750f))
        lineEntries.add(Entry(5f, 744f))
        lineEntries.add(Entry(6f, 801f))

        return lineEntries
    }

    private fun drawLineChartMacro() {
        val lineChart =  idlinechartmacro
        val lineEntries1 = getDataSet4()
        val lineEntries2 = getDataSet5()
        val lineEntries3 = getDataSet6()
        val lineDataSet1 = LineDataSet(lineEntries1, getString(R.string.proteins))
        val lineDataSet2 = LineDataSet(lineEntries2, getString(R.string.carbs))
        val lineDataSet3 = LineDataSet(lineEntries3, getString(R.string.fats))

        lineDataSet1.axisDependency = YAxis.AxisDependency.RIGHT
        lineDataSet1.isHighlightEnabled = true
        lineDataSet1.lineWidth = 2f
        lineDataSet1.setColors(resources.getColor(R.color.colorProteins))
        lineDataSet1.setCircleColor(resources.getColor(R.color.colorProteinsDark))
        lineDataSet1.circleRadius = 4f
        lineDataSet1.setDrawHighlightIndicators(true)
        lineDataSet1.highLightColor = Color.RED
        lineDataSet1.valueTextSize = 10f
        lineDataSet1.valueTextColor = Color.DKGRAY

        lineDataSet2.axisDependency = YAxis.AxisDependency.RIGHT
        lineDataSet2.isHighlightEnabled = true
        lineDataSet2.lineWidth = 2f
        lineDataSet2.setColors(resources.getColor(R.color.colorCarbs))
        lineDataSet2.setCircleColor(resources.getColor(R.color.colorCarbsDark))
        lineDataSet2.circleRadius = 4f
        lineDataSet2.setDrawHighlightIndicators(true)
        lineDataSet2.highLightColor = Color.RED
        lineDataSet2.valueTextSize = 10f
        lineDataSet2.valueTextColor = Color.DKGRAY

        lineDataSet3.axisDependency = YAxis.AxisDependency.RIGHT
        lineDataSet3.isHighlightEnabled = true
        lineDataSet3.lineWidth = 2f
        lineDataSet3.setColors(resources.getColor(R.color.colorFats))
        lineDataSet3.setCircleColor(resources.getColor(R.color.colorFatsDark))
        lineDataSet3.circleRadius = 4f
        lineDataSet3.setDrawHighlightIndicators(true)
        lineDataSet3.highLightColor = Color.RED
        lineDataSet3.valueTextSize = 10f
        lineDataSet3.valueTextColor = Color.DKGRAY

        val dataSets = arrayListOf(lineDataSet1, lineDataSet2, lineDataSet3)
        val lineData = LineData(dataSets as List<ILineDataSet>?)

        val legend = lineChart.legend
        legend.textSize = 12f
        legend.xEntrySpace = 16f

        lineChart.description.isEnabled = false
        lineChart.setDrawMarkers(true)
        //lineChart.setOnChartValueSelectedListener(this)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTH_SIDED
        lineChart.animateY(1500)
        lineChart.xAxis.isGranularityEnabled = true
        lineChart.xAxis.granularity = 1.0f
        lineChart.xAxis.textSize = 12f
        lineChart.xAxis.yOffset = 0.6f
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(x)
        lineChart.data = lineData
        lineChart.invalidate()
    }

    private fun getDataSet4() :  ArrayList<Entry>{
        val lineEntries = ArrayList<Entry>()
        lineEntries.add(Entry(0f, 82.2f))
        lineEntries.add(Entry(1f, 76.5f))
        lineEntries.add(Entry(2f, 86.1f))
        lineEntries.add(Entry(3f, 80.3f))
        lineEntries.add(Entry(4f, 78.3f))
        lineEntries.add(Entry(5f, 89.4f))
        lineEntries.add(Entry(6f, 96.6f))

        return lineEntries
    }

    private fun getDataSet5() :  ArrayList<Entry>{
        val lineEntries = ArrayList<Entry>()
        lineEntries.add(Entry(0f, 54.6f))
        lineEntries.add(Entry(1f, 61.3f))
        lineEntries.add(Entry(2f, 66.5f))
        lineEntries.add(Entry(3f, 58.4f))
        lineEntries.add(Entry(4f, 56.6f))
        lineEntries.add(Entry(5f, 50.0f))
        lineEntries.add(Entry(6f, 66.6f))

        return lineEntries
    }

    private fun getDataSet6() :  ArrayList<Entry>{
        val lineEntries = ArrayList<Entry>()
        lineEntries.add(Entry(0f, 102.2f))
        lineEntries.add(Entry(1f, 80.2f))
        lineEntries.add(Entry(2f, 78.0f))
        lineEntries.add(Entry(3f, 87.2f))
        lineEntries.add(Entry(4f, 76.1f))
        lineEntries.add(Entry(5f, 88.4f))
        lineEntries.add(Entry(6f, 80.1f))

        return lineEntries
    }
}
