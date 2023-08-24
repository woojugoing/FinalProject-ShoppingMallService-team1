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
import likelion.project.ipet_customer.databinding.FragmentProductCategoryBinding
import likelion.project.ipet_customer.databinding.ItemProductCardBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class ProductCategoryFragment : Fragment() {

    lateinit var fragmentProductCategoryBinding: FragmentProductCategoryBinding
    lateinit var mainActivity: MainActivity
    var lCategoryState: String? = null
    var sCategoryState: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductCategoryBinding = FragmentProductCategoryBinding.inflate(inflater)
        mainActivity = activity as MainActivity

//        sCategoryState = arguments?.getInt("sCategoryState")!!
//        lCategoryState = arguments?.getString("lCategoryState")!!
//        Log.d("sCs", "$sCategoryState , $lCategoryState")

        fragmentProductCategoryBinding.run {
            recyclerProductList.run {
                adapter = Adapter()
                layoutManager = GridLayoutManager(context, 2)
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
            return 10
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.textViewCardTitle.text = "${position+1} 번째 사료"
            holder.textViewCardCost.text = "${position+1}0000원"
        }
    }
}