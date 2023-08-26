package likelion.project.ipet_seller.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.R
import likelion.project.ipet_seller.databinding.FragmentProductListBinding
import likelion.project.ipet_seller.ui.main.MainActivity

class ProductListFragment : Fragment() {

    lateinit var fragmentProductListBinding: FragmentProductListBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: ProductListViewModel
    lateinit var productAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductListBinding = FragmentProductListBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(mainActivity)
        )[ProductListViewModel::class.java]
        productAdapter = ProductListAdapter(mainActivity, {
            viewModel.deleteProduct(it)
            viewModel.fetchProducts()
        })
        observe()

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
                    adapter = productAdapter
                    layoutManager = LinearLayoutManager(context)
                }
            }

            return fragmentProductListBinding.root
        }
    }

    private fun observe() = lifecycleScope.launch {
        viewModel.fetchProducts()
        launch {
            viewModel.uiState.collect {
                if (it.initProductList) {
                    productAdapter.subList(it.productList)
                    delay(200)
                    hideShimmerAndShowProducts()
                }
            }
        }

        launch {
            viewModel.event.collect {
                if (it) {
                    Snackbar.make(
                        fragmentProductListBinding.root,
                        "등록된 상품이 삭제되었습니다",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun hideShimmerAndShowProducts() {
        fragmentProductListBinding.run {
            recyclerViewProductList.visibility = View.VISIBLE
            shimmerFrameLayoutProductList.visibility = View.GONE
        }
    }
}