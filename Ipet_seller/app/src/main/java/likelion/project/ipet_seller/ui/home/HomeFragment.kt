package likelion.project.ipet_seller.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.R
import likelion.project.ipet_seller.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var fragmentHomeBinding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.fetchOrders("test")
        observe()
        return fragmentHomeBinding.root
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.uistate.collect {
                fragmentHomeBinding.run {
                    textViewHomeNewOrderCount.text = "신규 주문: ${it.orderCount}건"
                    textViewHomePreparingCount.text = it.preparingCount.toString()
                    textViewHomeDeliveringCount.text = it.deliveringCount.toString()
                    textViewHomeDeliveredCount.text = it.deliveredCount.toString()
                }
            }
        }
    }
}