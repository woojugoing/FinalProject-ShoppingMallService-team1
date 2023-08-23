package likelion.project.ipet_customer.ui.shoppingbasket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentShoppingBasketBinding
import likelion.project.ipet_customer.databinding.ItemShoppingBasketGroupBuyingBinding
import likelion.project.ipet_customer.databinding.ItemShoppingBasketProductBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class ShoppingBasketFragment : Fragment() {

    lateinit var fragmentShoppingBasketBinding: FragmentShoppingBasketBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentShoppingBasketBinding = FragmentShoppingBasketBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentShoppingBasketBinding.run {
            materialToolbarShoppingBasket.run {
                title = "장바구니"
                setNavigationIcon(R.drawable.ic_back_24dp)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.SHOPPING_BASKET_FRAGMENT)
                }
            }

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

        }

        return fragmentShoppingBasketBinding.root
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
            return 0
        }

        override fun onBindViewHolder(holder: ShoppingBasketHolder, position: Int) {
            holder.textViewProductName.text = "${position}강아지 사료"
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
            return 2
        }

        override fun onBindViewHolder(holder: ShoppingBasketGroupBuyingHolder, position: Int) {
            holder.textViewGroupBuyingName.text = "${position} 번째"
        }

    }

}