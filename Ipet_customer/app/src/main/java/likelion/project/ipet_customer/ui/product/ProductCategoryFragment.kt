package likelion.project.ipet_customer.ui.product

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import likelion.project.ipet_customer.databinding.FragmentProductCategoryBinding
import likelion.project.ipet_customer.databinding.ItemProductCardBinding
import likelion.project.ipet_customer.model.Joint
import likelion.project.ipet_customer.model.Product
import likelion.project.ipet_customer.ui.main.MainActivity

class ProductCategoryFragment : Fragment() {

    lateinit var fragmentProductCategoryBinding: FragmentProductCategoryBinding
    lateinit var mainActivity: MainActivity
    var lCategoryState: String? = null
    var sCategoryName: String? = null
    val productList = mutableListOf<Product>()
    val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductCategoryBinding = FragmentProductCategoryBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        sCategoryName = arguments?.getString("sCategoryName")!!
        lCategoryState = arguments?.getString("lCategoryState")!!
        Log.d("sCs", "$lCategoryState , $sCategoryName")

        fragmentProductCategoryBinding.run {
            recyclerProductList.run {
                adapter = Adapter()
                layoutManager = GridLayoutManager(context, 2)
            }

            when(lCategoryState){
                "사료" -> {setSmallCategory("주니어", "어덜트", "시니어", "다이어트", "건식", "습식")}
                "간식" -> {setSmallCategory("껌", "스낵", "육포", "캔", "비스켓", "기타")}
                "장난감" -> {setSmallCategory("공", "인형", "큐브", "훈련용품", "스크래쳐", "기타")}
                "의류" -> {setSmallCategory("레인코트", "신발", "외투", "원피스", "셔츠", "기타")}
                "집" -> {setSmallCategory("계단", "매트", "울타리", "안전문", "하우스", "기타")}
            }

            db.collection("Product")
                .whereEqualTo("productLCategory", lCategoryState)
                .whereEqualTo("productSCategory", sCategoryName)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val idx = document["productIdx"] as Long
                        val animalType = document["productAnimalType"] as String
                        val img = document["productImg"] as String
                        val price = document["productPrice"] as Long
                        val seller = document["productSeller"] as String
                        val text = document["productText"] as String
                        val title = document["productTitle"] as String
                        val stock = document["productStock"] as Long
                        val lCategory = document["productLCategory"] as String
                        val sCategory = document["productSCategory"] as String

                        val item = Product(animalType, idx, img, lCategory,price, sCategory, seller, stock, text, title)
                        productList.add(item)
                        fragmentProductCategoryBinding.recyclerProductList.adapter?.notifyDataSetChanged()
                    }
                }

        }
        return fragmentProductCategoryBinding.root
    }

    inner class Adapter: RecyclerView.Adapter<Adapter.Holder>() {
        inner class Holder(itemProductCardBinding: ItemProductCardBinding): RecyclerView.ViewHolder(itemProductCardBinding.root) {
            val imageViewCardThumbnail: ImageView
            val textViewCardTitle: TextView
            val textViewCardCost: TextView

            init {
                imageViewCardThumbnail = itemProductCardBinding.imageViewCardThumbnail
                textViewCardTitle = itemProductCardBinding.textViewCardTitle
                textViewCardCost = itemProductCardBinding.textViewCardCost
                itemProductCardBinding.root.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.PRODUCT_INFO_FRAGMENT, false, null)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val itemProductCardBinding = ItemProductCardBinding.inflate(layoutInflater)
            val holder = Holder(itemProductCardBinding)

            itemProductCardBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return holder
        }

        override fun getItemCount(): Int {
            return productList.size
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.textViewCardTitle.text = productList[position].productTitle
            holder.textViewCardCost.text = productList[position].productPrice.toString()
        }
    }
    fun setSmallCategory(cat1: String, cat2: String, cat3: String, cat4: String, cat5: String, cat6: String) {
        fragmentProductCategoryBinding.run {
            buttonCategory1.text = cat1
            buttonCategory2.text = cat2
            buttonCategory3.text = cat3
            buttonCategory4.text = cat4
            buttonCategory5.text = cat5
            buttonCategory6.text = cat6
        }
    }

    companion object {
        fun newInstance(lCategoryState: String, sCategoryName: String): ProductCategoryFragment {
            val fragment = ProductCategoryFragment()
            val args = Bundle()
            args.putString("sCategoryName", sCategoryName)
            args.putString("lCategoryState", lCategoryState)
            fragment.arguments = args
            return fragment
        }
    }
}