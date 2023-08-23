package likelion.project.ipet_seller.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import likelion.project.ipet_seller.databinding.ItemProductlistBinding
import likelion.project.ipet_seller.ui.main.MainActivity

class ProductListAdapter(private val mainActivity: MainActivity) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

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
                Toast.makeText(mainActivity, "삭제 버튼 클릭 이벤트", Toast.LENGTH_SHORT).show()
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

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {

    }
}