package likelion.project.ipet_seller.ui.order

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import likelion.project.ipet_seller.R
import likelion.project.ipet_seller.databinding.FragmentOrderStatusBinding
import likelion.project.ipet_seller.databinding.ItemOrderStatusBinding
import likelion.project.ipet_seller.ui.main.MainActivity

class OrderStatusFragment : Fragment() {

    lateinit var fragmentOrderStatusBinding: FragmentOrderStatusBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderStatusBinding = FragmentOrderStatusBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        fragmentOrderStatusBinding.run {
            toolbarOrderStatus.run {
                title = "주문/배송 관리"
                setNavigationIcon(R.drawable.ic_back_24dp)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.ORDER_STATUS_FRAGMENT)
                }
            }

            recyclerViewOrderStatus.run {
                adapter = Adapter()
                layoutManager = LinearLayoutManager(context)
            }
        }

        return fragmentOrderStatusBinding.root
    }

    inner class Adapter: RecyclerView.Adapter<Adapter.Holder>() {
        inner class Holder(itemOrderStatusBinding: ItemOrderStatusBinding): RecyclerView.ViewHolder(itemOrderStatusBinding.root) {
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
                    builder.setPositiveButton("확인", null)
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
            return 10
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.orderTitle.text = "강아지 사료 외 ${position}건"
            holder.orderDate.text = "2022/02/22 22:22"
            holder.orderNum.text = "0001-0222-${position}"
            holder.customer.text = "수취인 명 : 재능기부해조 ${position}번 팀원"
            holder.status.text = "배송 준비 중"
        }
    }
}