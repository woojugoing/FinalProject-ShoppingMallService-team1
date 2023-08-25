package likelion.project.ipet_customer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.ItemProductCardBinding
import likelion.project.ipet_customer.model.Product
import likelion.project.ipet_customer.ui.main.MainActivity
import java.text.NumberFormat
import java.util.Locale

class HomeBestAdapter(private val context: MainActivity,val products: MutableList<Product>) : RecyclerView.Adapter<HomeBestAdapter.BestViewHolder>() {

    inner class BestViewHolder(private val binding: ItemProductCardBinding) : RecyclerView.ViewHolder(binding.root){
        var linearLayout : LinearLayout
        var itemBestTitle : TextView
        var itemBestPrice : TextView
        var itemBestImg : ImageView
        var itemBestRank : TextView
        var itemBestHeart : ImageView

        init {
            val textViewBestRank = TextView(binding.root.context)

            linearLayout = binding.linearLayoutItemAddRank
            itemBestTitle = binding.textViewCardTitle
            itemBestPrice = binding.textViewCardCost
            itemBestImg = binding.imageViewCardThumbnail
            itemBestHeart = binding.imageViewCardHeart
            itemBestRank = textViewBestRank

            textViewBestRank.text = "1위"
            linearLayout.addView(itemBestRank,0)
            itemBestHeart.visibility = View.GONE

            // 상품 클릭 시 이벤트
            binding.root.setOnClickListener {
                var bundle = Bundle()
                val readProductIdx = products[adapterPosition].productIdx
                bundle.putLong("readProductIdx", readProductIdx)

                context.replaceFragment(MainActivity.PRODUCT_INFO_FRAGMENT, true, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBestAdapter.BestViewHolder {
        val binding = ItemProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val bestViewHolder = BestViewHolder(binding)

        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        binding.linearLayoutItemAddRank.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        return bestViewHolder
    }

    override fun getItemCount(): Int = minOf(products.size, 10)

    override fun onBindViewHolder(holder: BestViewHolder, position: Int) {

        val product = products[position]

        // 스타일 변경 코드
        holder.itemBestRank.typeface = ResourcesCompat.getFont(holder.itemView.context, R.font.pretendard_bold)
        holder.itemBestRank.setTextColor(ContextCompat.getColor(context, R.color.black))
        holder.itemBestRank.textSize = 12f

        holder.itemBestRank.text = "${position+1}위"
        holder.itemBestTitle.text = product.productTitle
        holder.itemBestPrice.text = "${formatNumberToCurrency(product.productPrice)}원"
    }

    // 가격 표현 형식 변환
    fun formatNumberToCurrency(number: Long): String {
        try {
            val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
            return numberFormat.format(number)
        } catch (e: NumberFormatException) {
            return number.toString()
        }
    }
}