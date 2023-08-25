package likelion.project.ipet_seller.ui.revenue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.coroutines.delay
import likelion.project.ipet_seller.R
import likelion.project.ipet_seller.databinding.FragmentRevenueBinding
import likelion.project.ipet_seller.model.Revenue
import likelion.project.ipet_seller.ui.main.MainActivity


class RevenueFragment : Fragment() {

    lateinit var fragmentRevenueBinding: FragmentRevenueBinding
    lateinit var viewModel: RevenueViewModel
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(
            this,
            RevenueViewModelFactory(mainActivity)
        )[RevenueViewModel::class.java]
        fragmentRevenueBinding = FragmentRevenueBinding.inflate(inflater)
        initToolbar()
        observe()
        return fragmentRevenueBinding.root
    }

    private fun initToolbar() {
        fragmentRevenueBinding.run {
            materialToolbarRevenue.run {
                setNavigationIcon(R.drawable.ic_back_24dp)
                setOnClickListener {
                    mainActivity.removeFragment(MainActivity.REVENUE_FRAGMENT)
                }
            }
        }
    }

    private fun observe() {
        viewModel.fetchOrders()
        viewModel.fetchProducts()
        lifecycleScope.launchWhenResumed {
            viewModel.uiState.collect {
                if (it.initOrderList && it.initProduectList) {
                    val revenueList = it.orderList.map { order ->
                        val totalPrice =
                            it.productList.filter { product -> product.productIdx == order.productIdx }
                                .sumOf { it.productPrice }
                        Revenue(order.orderDate, totalPrice)
                    }
                    val valueList = revenueList.groupBy { it.date }
                    val dateList = valueList.keys.map { it.toString() }
                    val orderList = valueList.values.map { it.size.toLong() }
                    val amountList = valueList.values.map { it.sumOf { it.price } }
                    setLineChart(
                        fragmentRevenueBinding.lineChartRevenueOrderCount,
                        "주문 수",
                        dateList,
                        orderList
                    )
                    setLineChart(
                        fragmentRevenueBinding.lineChartRevenueOrderAmount,
                        "매출",
                        dateList,
                        amountList
                    )
                    delay(1000)
                    hideShimmerAndShowChart()
                }
            }
        }
    }

    private fun hideShimmerAndShowChart() {
        fragmentRevenueBinding.apply {
            shimmerFrameLayoutRevenueOrderCount.visibility = View.GONE
            shimmerFrameLayoutRevenueOrderAmount.visibility = View.GONE
            constraintLayoutRevenueOrderCount.visibility = View.VISIBLE
            constraintLayoutRevenueOrderAmount.visibility = View.VISIBLE
        }
    }

    private fun setLineChart(
        lineChart: LineChart,
        label: String,
        xList: List<String>,
        yList: List<Long>
    ) {
        val xAxis = lineChart.xAxis
        val entries: MutableList<Entry> = getEntries(yList)
        val lineDataSet = LineDataSet(entries, label)
        lineDataSet.setup()
        xAxis.setup(xList)
        setHorizontalScrollView()
        lineChart.setup(lineDataSet)
    }

    private fun setHorizontalScrollView() {
        val horizontalScrollView = fragmentRevenueBinding.horizontalScrollViewRevenueOrderCount
        horizontalScrollView.post {
            horizontalScrollView.scrollTo(
                0,
                0
            )
        }
    }

    private fun XAxis.setup(xList: List<String>) {
        setDrawGridLines(false)
        setDrawAxisLine(true)
        setDrawLabels(true)
        position = XAxis.XAxisPosition.BOTTOM
        valueFormatter = XAxisCustomFormatter(changeDateText(xList))
        textColor = resources.getColor(R.color.black, null)
        textSize = 10f
        labelRotationAngle = 0f
        setAvoidFirstLastClipping(true)
        setLabelCount(xList.size, true)
    }

    private fun LineChart.setup(lineDataSet: LineDataSet) {
        axisRight.isEnabled = false
        axisLeft.isEnabled = false
        legend.isEnabled = true
        description.isEnabled = false
        isDragXEnabled = true
        isScaleYEnabled = false
        isScaleXEnabled = false
        data = LineData(lineDataSet)
        notifyDataSetChanged() //데이터 갱신
        invalidate() // view갱신
    }

    private fun LineDataSet.setup() {
        color = resources.getColor(R.color.rose_100, null)
        circleRadius = 5f
        lineWidth = 3f
        setCircleColor(resources.getColor(R.color.yellow_100, null))
        circleHoleColor = resources.getColor(R.color.yellow_100, null)
        setDrawHighlightIndicators(false)
        setDrawValues(true)
        valueTextColor = resources.getColor(R.color.black, null)
        valueFormatter = DefaultValueFormatter(0)
        valueTextSize = 10f
    }

    private fun getEntries(xList: List<Long>): MutableList<Entry> {
        val entries: MutableList<Entry> = mutableListOf()
        for (i in xList.indices) {
            entries.add(Entry(i.toFloat(), xList[i].toFloat()))
        }
        return entries
    }

    fun changeDateText(dataList: List<String>): List<String> {
        val dataTextList = ArrayList<String>()
        for (i in dataList.indices) {
            val dateText = dataList[i]
            dataTextList.add(dateText)
        }
        return dataTextList
    }

    class XAxisCustomFormatter(val xAxisData: List<String>) : ValueFormatter() {

        override fun getFormattedValue(value: Float): String {
            return xAxisData[(value).toInt()]
        }

    }
}
