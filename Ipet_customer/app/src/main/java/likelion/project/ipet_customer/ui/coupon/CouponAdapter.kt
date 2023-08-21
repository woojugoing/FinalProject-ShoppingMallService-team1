package likelion.project.ipet_customer.ui.coupon

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import likelion.project.ipet_customer.databinding.ItemCouponBinding
import likelion.project.ipet_customer.model.Coupon

class CouponAdapter : ListAdapter<Coupon, CouponAdapter.CouponViewHolder>(DIFFUTIL) {

    inner class CouponViewHolder(itemCouponBinding: ItemCouponBinding) :
        ViewHolder(itemCouponBinding.root) {
        var textViewType: TextView
        var textViewDiscount: TextView
        var textViewTerm: TextView

        init {
            textViewType = itemCouponBinding.textViewItemcouponType
            textViewDiscount = itemCouponBinding.textViewItemcouponDiscount
            textViewTerm = itemCouponBinding.textViewItemcouponTerm
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        val itemCouponBinding = ItemCouponBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = CouponViewHolder(itemCouponBinding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        val coupon = getItem(position)
        holder.run {
            textViewType.text = coupon.type
            textViewDiscount.text = coupon.discount
            textViewTerm.text = coupon.term
        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<Coupon>() {
            override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
                return oldItem.type == newItem.type
            }

            override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
                return oldItem == newItem
            }

        }
    }
}