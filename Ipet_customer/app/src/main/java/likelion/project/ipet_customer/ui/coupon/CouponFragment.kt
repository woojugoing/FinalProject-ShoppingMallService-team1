package likelion.project.ipet_customer.ui.coupon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentCouponBinding
import likelion.project.ipet_customer.model.Coupon
import likelion.project.ipet_customer.ui.main.MainActivity

class CouponFragment : Fragment() {

    lateinit var fragmentCouponBinding: FragmentCouponBinding
    lateinit var mainActivity: MainActivity
    private val couponAdapter = CouponAdapter()
    private val couponList =
        listOf(
            Coupon("웰컴 할인 쿠폰", "전 상품 10% 할인", "유효기간: 2023.04.01 - 2023.05.01"),
            Coupon("웰컴 배송비 쿠폰", "배송비 무료", "유효기간: 2023.04.01 - 2023.05.01")
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentCouponBinding = FragmentCouponBinding.inflate(inflater)
        initToolbar()
        initRecyclerView()
        return fragmentCouponBinding.root
    }

    fun initToolbar() {
        fragmentCouponBinding.run {
            materialToolbarCoupon.run {
                setNavigationIcon(R.drawable.ic_back_24dp)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.COUPON_FRAGMENT)
                }
            }
        }
    }

    fun initRecyclerView() {
        fragmentCouponBinding.run {
            recyclerViewCoupon.run {
                adapter = couponAdapter
                layoutManager = LinearLayoutManager(mainActivity)
            }
            couponAdapter.submitList(couponList)
        }
    }
}