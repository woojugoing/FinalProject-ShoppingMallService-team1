package likelion.project.ipet_seller.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import likelion.project.ipet_seller.databinding.ItemProductlistBinding
import likelion.project.ipet_seller.model.Product
import likelion.project.ipet_seller.ui.main.MainActivity

class ProductListAdapter(private val mainActivity: MainActivity, val onItemClickListener: (Product) -> Unit) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    private var productList = emptyList<Product>()

    fun subList(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    inner class ProductListViewHolder(private val binding: ItemProductlistBinding) : RecyclerView.ViewHolder(binding.root){
        var itemProductListTitle : TextView
        var itemProductListStock : TextView
        var itemProductListCategory : TextView
        var itemProductListThumbnail : ImageView
        var itemProductListDelete : Button

        init {
            itemProductListTitle = binding.textViewItemTitle
            itemProductListStock = binding.textViewItemStock
            itemProductListCategory = binding.textViewItemCategory
            itemProductListThumbnail = binding.imageViewItemThumbnail
            itemProductListDelete = binding.buttonItemDelete


            // 상품 삭제 버튼 클릭 시 이벤트
            itemProductListDelete.setOnClickListener {
                onItemClickListener(productList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListAdapter.ProductListViewHolder {
        val binding = ItemProductlistBinding.inflate(LayoutInflater.from(mainActivity), parent, false)
        val productListViewHolder = ProductListViewHolder(binding)

        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return productListViewHolder
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.run {
            val product = productList[position]
            val categories = mutableListOf<String>()
            categories.run {
                add(product.productAnimalType)
                add(product.productLcategory)
                add(product.productScategory)
            }
            itemProductListTitle.text = product.productTitle
            itemProductListStock.text = "재고: ${product.productStock}개"
            itemProductListCategory.text = categories.toList().joinToString("/")
            Glide.with(holder.itemView)
                .load(product.productImg[0])
                .into(holder.itemProductListThumbnail)
        }
    }
}