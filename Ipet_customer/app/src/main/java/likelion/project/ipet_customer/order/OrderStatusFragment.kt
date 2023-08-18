package likelion.project.ipet_customer.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderStatusBinding = FragmentOrderStatusBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentOrderStatusBinding.run {
            toolbarOrderStatus.run {
                title = "주문/배송"
                setNavigationIcon(R.drawable.ic_back_24dp)
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
            val imageViewOrderStatusThumbNail: ImageView
            val textViewOrderStatusName: TextView
            val textViewOrderStatusCost: TextView
            val textViewOrderStatusBrand: TextView
            val textViewOrderStatusStatus: TextView

            init {
                imageViewOrderStatusThumbNail = itemOrderStatusBinding.imageViewOrderStatusThumbNail
                textViewOrderStatusName = itemOrderStatusBinding.textViewOrderStatusName
                textViewOrderStatusCost = itemOrderStatusBinding.textViewOrderStatusCost
                textViewOrderStatusBrand = itemOrderStatusBinding.textViewOrderStatusBrand
                textViewOrderStatusStatus = itemOrderStatusBinding.textViewOrderStatusStatus
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
            holder.textViewOrderStatusStatus.text = "구매 완료"
        }
    }
}