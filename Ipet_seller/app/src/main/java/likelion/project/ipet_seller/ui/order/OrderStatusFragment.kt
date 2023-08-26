package likelion.project.ipet_seller.ui.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.R
import likelion.project.ipet_seller.databinding.FragmentOrderStatusBinding
import likelion.project.ipet_seller.databinding.ItemOrderStatusBinding
import likelion.project.ipet_seller.model.Order
import likelion.project.ipet_seller.model.Product
import likelion.project.ipet_seller.ui.main.MainActivity

class OrderStatusFragment : Fragment() {

    lateinit var fragmentOrderStatusBinding: FragmentOrderStatusBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: OrderStatusViewModel
    lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderStatusBinding = FragmentOrderStatusBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(
            this,
            OrderStatusViewModelFactory(mainActivity)
        )[OrderStatusViewModel::class.java]
        orderAdapter = OrderAdapter({
            viewModel.onOrderButtonClickEvent(it)
            viewModel.fetchOrdersWithMatchingOrderNumber()
        })

        initViewModel()
        observe()
        fragmentOrderStatusBinding.run {
            toolbarOrderStatus.run {
                title = "주문/배송 관리"
                setNavigationIcon(R.drawable.ic_back_24dp)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.ORDER_STATUS_FRAGMENT)
                }
            }

            recyclerViewOrderStatus.run {
                adapter = orderAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }

        return fragmentOrderStatusBinding.root
    }

    private fun initViewModel() {
        viewModel.run {
            fetchOrdersWithMatchingOrderNumber()
            fetchProducts()
        }
    }

    private fun observe() = lifecycleScope.launch {
        launch {
            viewModel.uiState.collect {
                if (it.initOrderList) {
                    orderAdapter.subList(it.orderList, it.productList)
                    delay(200)
                    hideShimmerAndShowOrders()
                }
            }
        }

        launch {
            viewModel.event.collect {
                Snackbar.make(fragmentOrderStatusBinding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideShimmerAndShowOrders() {
        fragmentOrderStatusBinding.run {
            recyclerViewOrderStatus.visibility = View.VISIBLE
            shimmerFrameLayoutOrderStatusOrderCount.visibility = View.GONE
        }
    }

    inner class OrderAdapter(private val onOrderButtonClickEvent: (List<Order>) -> Unit ) : RecyclerView.Adapter<OrderAdapter.Holder>() {

        private var orderList = emptyMap<Long,List<Order>>()
        private var productList = emptyList<Product>()

        fun subList(orderList: Map<Long,List<Order>>, productList: List<Product>) {
            this.orderList = orderList
            this.productList = productList
            notifyDataSetChanged()
        }

        inner class Holder(itemOrderStatusBinding: ItemOrderStatusBinding) :
            RecyclerView.ViewHolder(itemOrderStatusBinding.root) {
            val orderTitle: TextView
            val orderDate: TextView
            val orderNum: TextView
            val customer: TextView
            val status: TextView
            val thumbNail: ImageView
            val confirm: Button

            init {
                orderTitle = itemOrderStatusBinding.textViewOrderStatusOrderTitle
                orderDate = itemOrderStatusBinding.textViewOrderStatusOrderDate
                orderNum = itemOrderStatusBinding.textViewOrderStatusOrderNum
                customer = itemOrderStatusBinding.textViewOrderStatusCustomer
                status = itemOrderStatusBinding.textViewOrderStatusStatus
                thumbNail = itemOrderStatusBinding.imageViewOrderStatusThumbNail
                confirm = itemOrderStatusBinding.buttonOrderStatusConfirm
                confirm.setOnClickListener {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("해당 건의 주문에 대해서 발송 처리 하시겠습니까?")
                    builder.setPositiveButton("확인") { dialog, _ ->
                        onOrderButtonClickEvent(orderList.values.toList()[adapterPosition])
                        dialog.dismiss()
                    }
                    builder.setNegativeButton("취소", null)
                    builder.show()
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val itemOrderStatusBinding = ItemOrderStatusBinding.inflate(layoutInflater)
            val holder = Holder(itemOrderStatusBinding)

            itemOrderStatusBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return holder
        }

        override fun getItemCount(): Int {
            return orderList.size
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val orders = orderList.values.toList()[position]
            val product = productList.find { it.productIdx == orders[0].productIdx } ?: Product()
            holder.orderTitle.text = "${product.productTitle} 외 ${orders.size}건"
            holder.orderDate.text = orders[0].orderDate.toString()
            holder.orderNum.text = orders[0].orderNumber.toString()
            holder.customer.text = String.format("수취인 명 : ${orders[0].orderRecipient}")
            holder.status.text = when (OrderStatus.of(orders[0].orderState).number) {
                0 -> "배송준비중"
                1 -> "배송중"
                2 -> "배송완료"
                else -> "배송처리중"
            }
            Glide.with(holder.itemView)
                .load(product.productImg)
                .into(holder.thumbNail)
            if (orders[0].orderState == OrderStatus.BEFORE_PROCESSING.number) {
                holder.confirm.visibility = View.VISIBLE
            } else {
                holder.confirm.visibility = View.GONE
            }
        }
    }
}