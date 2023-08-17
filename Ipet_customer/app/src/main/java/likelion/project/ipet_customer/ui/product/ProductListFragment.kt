package likelion.project.ipet_customer.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentProductListBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class ProductListFragment : Fragment() {

    lateinit var fragmentProductListBinding: FragmentProductListBinding
    lateinit var mainActivity: MainActivity
    val fragmentList = mutableListOf<Fragment>()
    val productListName = arrayOf("사료", "간식", "장난감", "용품", "집")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductListBinding = FragmentProductListBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        fragmentProductListBinding.run {
            toolbarProductList.run {
                title = "상품 목록"
                setNavigationIcon(R.drawable.ic_back_24dp)
            }

            fragmentList.run {
                add(ProductFoodFragment())
                add(ProductFoodFragment())
                add(ProductFoodFragment())
                add(ProductFoodFragment())
                add(ProductFoodFragment())
            }

            pagerProductList.adapter = TabAdapterClass(mainActivity)

            val tabLayoutMediator = TabLayoutMediator(
                tabsProductList, pagerProductList){ tab: TabLayout.Tab, i: Int ->
            tab.text = productListName[i]
            }

            tabLayoutMediator.attach()

        }
        return fragmentProductListBinding.root
    }

    inner class TabAdapterClass(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }

}