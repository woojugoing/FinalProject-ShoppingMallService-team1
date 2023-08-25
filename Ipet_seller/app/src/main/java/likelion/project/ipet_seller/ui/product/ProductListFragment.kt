package likelion.project.ipet_seller.ui.product

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import likelion.project.ipet_seller.R
import likelion.project.ipet_seller.databinding.FragmentProductListBinding
import likelion.project.ipet_seller.ui.main.MainActivity

class ProductListFragment : Fragment() {

    lateinit var fragmentProductListBinding: FragmentProductListBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductListBinding = FragmentProductListBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentProductListBinding.run {
            toolbarProductList.run {
                inflateMenu(R.menu.menu_productlist)
                navigationIcon.run {
                    setOnClickListener {
                        mainActivity.removeFragment(MainActivity.PRODUCT_LIST_FRAGMENT)
                    }
                }
                val color = ContextCompat.getColorStateList(mainActivity, R.color.brown_200)
                val menuItem = menu.findItem(R.id.item_add_product)

                val icon = menuItem.icon
                icon?.let {
                    val wrappedDrawable = DrawableCompat.wrap(it)
                    DrawableCompat.setTintList(wrappedDrawable, color)
                    menuItem.icon = wrappedDrawable
                }

                // 우측 상단 상품 추가 버튼 클릭 시
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.item_add_product -> {
                            mainActivity.replaceFragment(
                                MainActivity.REGISTRATION_FRAGMENT,
                                true,
                                null
                            )
                        }

                    }
                    false
                }

                recyclerViewProductList.run {
                    adapter = ProductListAdapter(mainActivity)
                    layoutManager = LinearLayoutManager(context)
                }
            }

            return fragmentProductListBinding.root
        }
    }
}