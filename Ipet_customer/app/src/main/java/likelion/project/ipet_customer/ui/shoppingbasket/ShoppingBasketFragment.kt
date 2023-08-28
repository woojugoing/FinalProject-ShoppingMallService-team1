package likelion.project.ipet_customer.ui.shoppingbasket

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentShoppingBasketBinding
import likelion.project.ipet_customer.databinding.ItemShoppingBasketGroupBuyingBinding
import likelion.project.ipet_customer.databinding.ItemShoppingBasketProductBinding
import likelion.project.ipet_customer.model.Joint
import likelion.project.ipet_customer.model.Product
import likelion.project.ipet_customer.ui.login.LoginViewModel
import likelion.project.ipet_customer.ui.main.MainActivity

class ShoppingBasketFragment : Fragment() {

    lateinit var fragmentShoppingBasketBinding: FragmentShoppingBasketBinding
    lateinit var mainActivity: MainActivity

    val productDataList = mutableListOf<Product>()
    val jointDataList = mutableListOf<Joint>()
    val db = Firebase.firestore
    val customerId = LoginViewModel.customer.customerId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentShoppingBasketBinding = FragmentShoppingBasketBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentShoppingBasketBinding.run {
            materialToolbarShoppingBasket.run {
                title = "장바구니"

                var fragmentState = arguments?.getString("fragmentState")
                // 내정보에서 장바구니를 클릭했을 때에만, 장바구니 화면에서 뒤로가기 버튼이 보이게함
                if (fragmentState == "Shopping") {
                    setNavigationIcon(R.drawable.ic_back_24dp)
                    setNavigationOnClickListener {
                        mainActivity.removeFragment(MainActivity.SHOPPING_BASKET_FRAGMENT)
                    }
                } else {
                    navigationIcon = null
                }
            }

            Log.d("ShoppingBasketFragment", "customerId : ${customerId}")
            checkProductDB()
            checkJoinDB()

            recyclerGoodsList.run {
                adapter = ShoppingBasketAdapter()
                layoutManager = LinearLayoutManager(context)

                // recyclerGoodsList 가 비어있을 때, 아래의 화면이 나타나개함
                if(adapter?.itemCount == 0) {
                    fragmentShoppingBasketBinding.linearLayoutEmpty.visibility = View.VISIBLE
                }
            }

            recyclerGroupGoodsList.run {
                adapter = ShoppingBasketGroupBuyingAdapter()
                layoutManager = LinearLayoutManager(context)

                // recyclerGroupGoodsList 가 비어있을 때, 아래의 화면이 나타나개함
                if(adapter?.itemCount == 0) {
                    fragmentShoppingBasketBinding.linearLayoutEmptyGroup.visibility = View.VISIBLE
                }
            }

            // 담아둔 상품이 없을 때, 쇼핑하러 가기 버튼
            buttonShoppingBasketGo.run {
                setOnClickListener {
                    mainActivity.selectBottomNavigationItem(R.id.item_bottom_home)
                    mainActivity.replaceFragment(MainActivity.HOME_FRAGMENT, false, null)
                }
            }

            // 공동 구매 상품을 안담았을 때, 공동구매 하러가기 버튼
            buttonShoppingBasketGroupJoin.run {
                setOnClickListener {
                    mainActivity.selectBottomNavigationItem(R.id.item_bottom_joinT)
                    mainActivity.replaceFragment(MainActivity.PRODUCT_JOINT_LIST_FRAGMENT, false, null)
                }
            }

            // 결제하기 버튼
            buttonShoppingBasketPayment.run {
                setOnClickListener {
                    mainActivity.activityMainBinding.bottomNavigation.visibility = View.GONE
                    mainActivity.replaceFragment(MainActivity.PAYMENT_FRAGMENT, true, null)
                }
            }

        }

        return fragmentShoppingBasketBinding.root
    }

    private fun checkProductDB() {
        productDataList.clear()

        db.collection("Cart")
            .whereEqualTo("buyerId", customerId) // 구매자의 아이디 받아오기
            .whereEqualTo("productIdx", "H338D04FOFDkay8LYyNW")    // Todo 공동구매의 제품번호를 받아오도록하기
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val productIdx = document["productIdx"] as String

                    db.collection("Product")
                        .whereEqualTo("productIdx", productIdx)
                        .get(com.google.firebase.firestore.Source.CACHE)
                        .addOnSuccessListener {product ->
                            for (documentProduct in product) {

                                val productAnimalType = documentProduct["productAnimalType"] as String
                                val productIdx = documentProduct["productIdx"] as String
                                val productImg = documentProduct["productImg"] as ArrayList<*>
                                val productLCategory  = documentProduct["productLcategory"] as String
                                val productPrice = documentProduct["productPrice"] as Long
                                val productSCategory = documentProduct["productScategory"] as String
                                val productSeller = documentProduct["productSeller"] as String
                                val productStock = documentProduct["productStock"] as Long
                                val productText = documentProduct["productText"] as String
                                val productTitle = documentProduct["productTitle"] as String

                                val product = Product(productAnimalType, productIdx, productImg, productLCategory, productPrice, productSCategory, productSeller, productStock, productText, productTitle)
                                productDataList.add(product)

                                Log.d("ShoppingBasketFragment", "productDataList : ${productDataList}")
                            }
                            fragmentShoppingBasketBinding.recyclerGoodsList.adapter?.notifyDataSetChanged()
                            if(productDataList.isEmpty()) {
                                fragmentShoppingBasketBinding.linearLayoutEmpty.visibility = View.VISIBLE
                            } else {
                                fragmentShoppingBasketBinding.linearLayoutEmpty.visibility = View.GONE
                            }

                        }
                }

            }
            .addOnFailureListener { exception ->
                Log.d("ShoppingBasketFragment", "Error getting documents: ", exception)
            }
    }

    private fun checkJoinDB() {
        jointDataList.clear()

        db.collection("Cart")
            .whereEqualTo("buyerId", customerId) // 구매자의 아이디 받아오기
            .whereEqualTo("productIdx", "1")    // Todo 공동구매의 제품번호를 받아오도록하기
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val jointIdx = document["productIdx"] as String
                    Log.d("ShoppingBasketFragment", "jointIdx : ${jointIdx}")

                    // Joint 컬렉션에서 jointIdx와 값이 같은 문서를 찾기
                    db.collection("Joint")
                        .whereEqualTo("jointIdx", jointIdx.toLong())
                        .get(com.google.firebase.firestore.Source.CACHE)
                        .addOnSuccessListener {jointResult ->
                            for (jointDocument in jointResult) {
                                val jointAnimalType = jointDocument["jointAnimalType"] as String
                                val jointIdx = jointDocument["jointIdx"] as Long
                                val jointImg = jointDocument["jointImg"] as ArrayList<*>
                                val jointMember  = jointDocument["jointMember"] as Long
                                val jointPrice = jointDocument["jointPrice"] as Long
                                val jointSeller = jointDocument["jointSeller"] as String
                                val jointTerm = jointDocument["jointTerm"] as String
                                val jointText = jointDocument["jointText"] as String
                                val jointTitle = jointDocument["jointTitle"] as String
                                val jointTotalMember = jointDocument["jointTotalMember"] as Long

                                val product = Joint(jointAnimalType, jointIdx, jointImg, jointMember, jointPrice, jointSeller, jointTerm, jointText, jointTitle, jointTotalMember)
                                jointDataList.add(product)

                                Log.d("ShoppingBasketFragment", "jointDataList : ${jointDataList}")
                            }
                            fragmentShoppingBasketBinding.recyclerGroupGoodsList.adapter?.notifyDataSetChanged()
                            if(jointDataList.isEmpty()) {
                                fragmentShoppingBasketBinding.linearLayoutEmptyGroup.visibility = View.VISIBLE
                            } else {
                                fragmentShoppingBasketBinding.linearLayoutEmptyGroup.visibility = View.GONE
                            }


                        }


                }
            }
            .addOnFailureListener { exception ->
                Log.d("ShoppingBasketFragment", "Error getting documents: ", exception)
            }

    }

    inner class ShoppingBasketAdapter : RecyclerView.Adapter<ShoppingBasketAdapter.ShoppingBasketHolder>() {

        inner class ShoppingBasketHolder(itemShoppingBasketProductBinding: ItemShoppingBasketProductBinding) : RecyclerView.ViewHolder(itemShoppingBasketProductBinding.root) {

            val checkBoxProdut : CheckBox
            val imageViewProduct : ImageView
            val textViewProductName : TextView
            val textViewProductCount : TextView
            val textViewProductPrice : TextView

            init {
                checkBoxProdut = itemShoppingBasketProductBinding.checkBoxProdut
                imageViewProduct = itemShoppingBasketProductBinding.imageViewProduct
                textViewProductName = itemShoppingBasketProductBinding.textViewProductName
                textViewProductCount = itemShoppingBasketProductBinding.textViewProductCount
                textViewProductPrice = itemShoppingBasketProductBinding.textViewProductPrice
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingBasketHolder {
            val itemShoppingBasketProductBinding = ItemShoppingBasketProductBinding.inflate(layoutInflater)
            val shoppingBasketHolder = ShoppingBasketHolder(itemShoppingBasketProductBinding)

            itemShoppingBasketProductBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return shoppingBasketHolder
        }

        override fun getItemCount(): Int {
            return productDataList.size
        }

        override fun onBindViewHolder(holder: ShoppingBasketHolder, position: Int) {
            holder.textViewProductName.text = productDataList[position].productTitle
            holder.textViewProductCount.text = "수량 ${productDataList[position].productStock}개"
            holder.textViewProductPrice.text = "${mainActivity.formatNumberToCurrency(productDataList[position].productPrice)}원"

            // productImg의 첫 번째 항목을 가져옴
            val imageUrl = productDataList[position].productImg?.get(0)

            // imageUrl이 null이 아닐 때만 Glide로 로드
            imageUrl?.let {
                Glide.with(holder.itemView.context)
                    .load(it)
                    .into(holder.imageViewProduct)
            }
        }

    }

    inner class ShoppingBasketGroupBuyingAdapter : RecyclerView.Adapter<ShoppingBasketGroupBuyingAdapter.ShoppingBasketGroupBuyingHolder>() {

        inner class ShoppingBasketGroupBuyingHolder(itemShoppingBasketGroupBuyingBinding: ItemShoppingBasketGroupBuyingBinding) : RecyclerView.ViewHolder(itemShoppingBasketGroupBuyingBinding.root) {

            val checkBoxGroupBuying : CheckBox
            val imageViewGroupBuying : ImageView
            val textViewGroupBuyingName : TextView
            val textViewGroupBuyingCount : TextView
            val textViewGroupBuyingPeriod : TextView
            val textViewGroupBuyingPrice : TextView

            init {
                checkBoxGroupBuying = itemShoppingBasketGroupBuyingBinding.checkBoxGroupBuying
                imageViewGroupBuying = itemShoppingBasketGroupBuyingBinding.imageViewGroupBuying
                textViewGroupBuyingName = itemShoppingBasketGroupBuyingBinding.textViewGroupBuyingName
                textViewGroupBuyingCount = itemShoppingBasketGroupBuyingBinding.textViewGroupBuyingCount
                textViewGroupBuyingPeriod = itemShoppingBasketGroupBuyingBinding.textViewGroupBuyingPeriod
                textViewGroupBuyingPrice = itemShoppingBasketGroupBuyingBinding.textViewGroupBuyingPrice

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingBasketGroupBuyingHolder {
            val itemShoppingBasketGroupBuyingBinding = ItemShoppingBasketGroupBuyingBinding.inflate(layoutInflater)
            val shoppingBasketGroupBuyingHolder = ShoppingBasketGroupBuyingHolder(itemShoppingBasketGroupBuyingBinding)

            itemShoppingBasketGroupBuyingBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return shoppingBasketGroupBuyingHolder
        }

        override fun getItemCount(): Int {
            return jointDataList.size
        }

        override fun onBindViewHolder(holder: ShoppingBasketGroupBuyingHolder, position: Int) {
            holder.textViewGroupBuyingName.text = jointDataList[position].jointTitle
            holder.textViewGroupBuyingCount.text = "남은 사람 : ${jointDataList[position].jointTotalMember - jointDataList[position].jointMember}명"
            holder.textViewGroupBuyingPeriod.text = jointDataList[position].jointTerm
            holder.textViewGroupBuyingPrice.text = "${mainActivity.formatNumberToCurrency(jointDataList[position].jointPrice)}원"

            val imageUrl = jointDataList[position].jointImg?.get(0)

            imageUrl?.let {
                Glide.with(holder.itemView.context)
                    .load(it)
                    .into(holder.imageViewGroupBuying)
            }
        }

    }

}