package com.ajijul.givemeforcast.ui.main.forecast

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ajijul.givemeforcast.R
import com.ajijul.givemeforcast.databinding.FragmentPerdayReportBinding
import com.ajijul.givemeforcast.models.forecast.ForecastBaseModel
import com.ajijul.givemeforcast.models.forecast.ThreeHoursModel
import com.ajijul.givemeforcast.utils.base.BaseFragment
import com.ajijul.givemeforcast.utils.base.ResultWrapper
import com.ajijul.givemeforcast.utils.ViewModelProviderFactory
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.fragment_perday_report.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ForecastPerDayReportFragment(val position: Int) : BaseFragment() {

    private lateinit var binding: FragmentPerdayReportBinding
    lateinit var forevcastViewModel: ForecastViewModel
    private val format = SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.getDefault())
    private val outFormat = SimpleDateFormat("hh:mm", Locale.getDefault())

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_perday_report, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        forevcastViewModel =
            ViewModelProvider(
                activity!!,
                viewModelProviderFactory
            ).get(ForecastViewModel::class.java)
        configureChart()
        observeGroupData()
    }

    private fun observeGroupData() {

//        forevcastViewModel.observeGroupData()
//            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//
//                if (it != null) {
//                    it[position]?.let {
//                        generateChart(it)
//                    }
//                }
//            })
    }

    fun configureChart() {
        mpLineChart.axisLeft.setDrawGridLines(false)
        mpLineChart.axisLeft.isEnabled = false
        mpLineChart.axisRight.isEnabled = false
        mpLineChart.axisRight.setDrawGridLines(false)
        mpLineChart.xAxis.setDrawGridLines(false)
        mpLineChart.legend.isEnabled = false
        mpLineChart.xAxis.textColor = Color.WHITE
        mpLineChart.description.isEnabled = false
        mpLineChart.setPinchZoom(false)
        mpLineChart.isDoubleTapToZoomEnabled = false
        mpLineChart.setTouchEnabled(false)
        mpLineChart.setVisibleXRangeMaximum(100f)
        generateChart()
    }


    private fun generateChart() {
        val todayData = forevcastViewModel.observeGroupData().value
        val tempModels: List<ThreeHoursModel> = todayData?.values?.elementAt(position) ?: return

        val entries =
            ArrayList<Entry>()
        val xAXES = ArrayList<String>()
        llFooter.setWeightSum(tempModels.size.toFloat())
        for (i in tempModels.indices) {
            entries.add(
                Entry(
                    i.toFloat(),
                    tempModels[i].main.temp.toString().replace("\u00B0", "").toFloat()
                )
            )
            val textView = TextView(context)
            val params = TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            if (i == 0 && position == 0) textView.text = "Now" else {
                textView.text = outFormat.format(format.parse(tempModels[i].dt_txt) ?: "")
                textView.textSize = 10f
            }
            textView.layoutParams = params
            textView.setTextColor(Color.WHITE)
            llFooter.addView(textView)
            llFooter.invalidate()
            xAXES.add(tempModels[i].main.temp.toString())
        }
        val lineDataSet = LineDataSet(entries, "")
        lineDataSet.setDrawCircles(false)
        lineDataSet.color = Color.YELLOW
        lineDataSet.lineWidth = 5f
        lineDataSet.setDrawValues(false)
        //  lineDataSet.setDrawCubic(true);
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        val lineData = LineData(lineDataSet)
        val xAxis = mpLineChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(xAXES)
        xAxis.position = XAxis.XAxisPosition.TOP
        mpLineChart.data = lineData
        binding.data = tempModels[0]
        binding.main = (forevcastViewModel.getForecastResult().value
                as ResultWrapper.Success<ForecastBaseModel>).value
    }
}