package likelion.project.ipet_customer.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentProductJointListBinding
import likelion.project.ipet_customer.databinding.ItemProductCardBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class ProductJointListFragment : Fragment() {

    lateinit var fragmentProductJointListBinding: FragmentProductJointListBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductJointListBinding = FragmentProductJointListBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentProductJointListBinding.run {
            toolbarProductJointList.run {
                title = "공동 구매 상품 리스트"
                setNavigationIcon(R.drawable.ic_back_24dp)
            }
            recyclerProductJointList.run {
                adapter = Adapter()
                layoutManager = GridLayoutManager(context, 2)
            }
        }
        return fragmentProductJointListBinding.root
    }

    inner class Adapter: RecyclerView.Adapter<Adapter.Holder>() {
        inner class Holder(rowBinding: ItemProductCardBinding): RecyclerView.ViewHolder(rowBinding.root) {
            val imageViewCardThumbnail: ImageView
            val textViewCardTitle: TextView
            val textViewCardCost: TextView

            init {
                imageViewCardThumbnail = rowBinding.imageViewCardThumbnail
                textViewCardTitle = rowBinding.textViewCardTitle
                textViewCardCost = rowBinding.textViewCardCost
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val rowBinding = ItemProductCardBinding.inflate(layoutInflater)
            val holder = Holder(rowBinding)

            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
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