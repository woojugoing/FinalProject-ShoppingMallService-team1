package likelion.project.ipet_customer.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentOrderStatusBinding
import likelion.project.ipet_customer.databinding.ItemOrderStatusBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class OrderStatusFragment : Fragment() {

    lateinit var fragmentOrderStatusBinding: FragmentOrderStatusBinding
    lateinit var mainActivity: MainActivity
    var fragmentState = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderStatusBinding = FragmentOrderStatusBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentState = arguments?.getString("fragmentState")!!

        fragmentOrderStatusBinding.run {
            toolbarOrderStatus.run {
                title = when(fragmentState) {
                    "Detail" -> "주문/배송"
                    "Cancel" -> "취소/반품/교환"
                    "Review" -> "내 리뷰"
                    else -> null
                }
                setNavigationIcon(R.drawable.ic_back_24dp)
            }
            recyclerViewOrderStatus.run {
                adapter = OrderStatusAdapter(fragmentState)
                layoutManager = LinearLayoutManager(context)
            }
        }

        return fragmentOrderStatusBinding.root
    }

    inner class OrderStatusAdapter(private val fragmentState: String) : RecyclerView.Adapter<OrderStatusAdapter.Holder>() {
        inner class Holder(itemOrderStatusBinding: ItemOrderStatusBinding) : RecyclerView.ViewHolder(itemOrderStatusBinding.root) {
            val imageViewOrderStatusThumbNail: ImageView
            val textViewOrderStatusName: TextView
            val textViewOrderStatusCost: TextView
            val textViewOrderStatusBrand: TextView
            val textViewOrderStatusStatus: TextView
            val buttonOrderStatusReview: Button?
            val buttonOrderStatusCancel: Button?

            init {
                imageViewOrderStatusThumbNail = itemOrderStatusBinding.imageViewOrderStatusThumbNail
                textViewOrderStatusName = itemOrderStatusBinding.textViewOrderStatusName
                textViewOrderStatusCost = itemOrderStatusBinding.textViewOrderStatusCost
                textViewOrderStatusBrand = itemOrderStatusBinding.textViewOrderStatusBrand
                textViewOrderStatusStatus = itemOrderStatusBinding.textViewOrderStatusStatus
                buttonOrderStatusReview = itemOrderStatusBinding.buttonOrderStatusReview
                buttonOrderStatusCancel = itemOrderStatusBinding.buttonOrderStatusCancel
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
            return 10
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.textViewOrderStatusBrand.text = "브랜드$position"
            holder.textViewOrderStatusName.text = "$position 번째 사료"
            holder.textViewOrderStatusCost.text = "${position}0,000원"

            when (fragmentState) {
                "Detail" -> {
                    holder.textViewOrderStatusStatus.text = "구매 완료"
                    holder.buttonOrderStatusReview?.visibility = View.VISIBLE
                    holder.buttonOrderStatusCancel?.visibility = View.VISIBLE
                }
                "Cancel" -> {
                    holder.textViewOrderStatusStatus.text = "취소 완료"
                    holder.buttonOrderStatusReview?.visibility = View.INVISIBLE
                    holder.buttonOrderStatusCancel?.visibility = View.INVISIBLE
                }
                "Review" -> {
                    holder.textViewOrderStatusStatus.text = "리뷰 대기"
                    holder.buttonOrderStatusReview?.visibility = View.INVISIBLE
                    holder.buttonOrderStatusCancel?.visibility = View.INVISIBLE
                }
                else -> null
            }
        }
    }
}