package likelion.project.ipet_seller.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.databinding.FragmentHomeBinding
import likelion.project.ipet_seller.ui.main.MainActivity

class HomeFragment : Fragment() {

    lateinit var fragmentHomeBinding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater)
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(mainActivity))[HomeViewModel::class.java]
        initEvent()
        initViewModel()
        observe()
        return fragmentHomeBinding.root
    }

    private fun initEvent() {
        fragmentHomeBinding.run {
            onNewOrderClickEvent()
            onOrderStatusClickEvent()
            onProductListClickEvent()
            onRegisterProductClickEvent()
            onRevenueClickEvent()
            onLogoutClickEvent()
        }
    }

    private fun FragmentHomeBinding.onNewOrderClickEvent() {
        imageViewHomeOrderCountArrow.run {
            setOnClickListener {
                mainActivity.replaceFragment(MainActivity.ORDER_STATUS_FRAGMENT, true, null)
            }
        }
    }

    private fun FragmentHomeBinding.onOrderStatusClickEvent() {
        imageViewHomeOrderStatus.run {
            setOnClickListener {
                mainActivity.replaceFragment(MainActivity.ORDER_STATUS_FRAGMENT, true, null)
            }
        }
    }

    private fun FragmentHomeBinding.onLogoutClickEvent() {
        cardViewHomeLogout.run {
            setOnClickListener {
                viewModel.onLogoutClickEvent()
            }
        }
    }

    private fun FragmentHomeBinding.onRevenueClickEvent() {
        cardViewHomeRevenue.run {
            setOnClickListener {
                mainActivity.replaceFragment(MainActivity.REVENUE_FRAGMENT, true, null)
            }
        }
    }

    private fun FragmentHomeBinding.onRegisterProductClickEvent() {
        cardViewHomeRegisterProduct.run {
            setOnClickListener {
                mainActivity.replaceFragment(MainActivity.REGISTRATION_FRAGMENT, true, null)
            }
        }
    }

    private fun FragmentHomeBinding.onProductListClickEvent() {
        cardViewHomeProductList.run {
            setOnClickListener {
                mainActivity.replaceFragment(MainActivity.PRODUCT_LIST_FRAGMENT, true, null)
            }
        }
    }

    private fun initViewModel() {
        viewModel.run {
            fetchOrders()
            fetchSeller()
            fetchOrdersWithMatchingOrderNumber()
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.uistate.collect {
                fragmentHomeBinding.run {
                    textViewHomeSellerName.text = "판매자 ${it.seller?.sellerName} 님"
                    textViewHomeNewOrderCount.text = "신규 주문: ${it.beforeProcessingCount}건"
                    textViewHomePreparingCount.text = it.preparingCount.toString()
                    textViewHomeDeliveringCount.text = it.deliveringCount.toString()
                    textViewHomeDeliveredCount.text = it.deliveredCount.toString()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.event.collect {
                mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, null)
                Snackbar.make(fragmentHomeBinding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}