package likelion.project.ipet_customer.ui.product

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentProductInfoBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class ProductInfoFragment : Fragment() {

    lateinit var fragmentProductInfoBinding: FragmentProductInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductInfoBinding = FragmentProductInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentProductInfoBinding.run {

            val sheetBehavior = BottomSheetBehavior.from(includeProductinfoBottomsheet.bottomsheetProductinfo)

            toolbarProductInfo.run {

                setNavigationIcon(R.drawable.ic_back_24dp)
            }

            // 원가 가격 표시
            textviewProductinfoCostprice.paintFlags = textviewProductinfoCostprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            viewpager2ProductinfoThumbnail.run {
                adapter = ProductInfoFragmentStateAdapter(mainActivity)
            }

            // 상세 이미지
            imageviewProductinfoDetail.run {
                setImageResource(R.drawable.img_dog_food_detail)
            }

            // 구매하기 버튼
            buttonProductinfoBuy.run {
                setOnClickListener {
                    sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

            // bottomSheet
            includeProductinfoBottomsheet.run {
                // 원가 가격 표시
                textviewBottomsheetCostprice.paintFlags = textviewProductinfoCostprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                var num = 0
                // 왼쪽 화살표 버튼
                imagebuttonBottomsheetMinus.setOnClickListener {
                    if(num > 0){
                        num--
                    }
                }
                // 오른쪽 화살표 버튼
                imagebuttonBottomsheetPlus.setOnClickListener {
                    num++
                }
            }
        }

        return fragmentProductInfoBinding.root
    }

    // viewPager2 Adapter
    inner class ProductInfoFragmentStateAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){
        // 보여줄 페이지 수
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> ProductInfoViewPagerFragment(R.drawable.img_dog_food1)
                1 -> ProductInfoViewPagerFragment(R.drawable.img_dog_food2)
                else -> ProductInfoViewPagerFragment(R.drawable.img_dog_food3)
            }
        }

    }
}