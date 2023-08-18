package likelion.project.ipet_customer.ui.product

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentProductInfoBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class ProductInfoFragment : Fragment() {

    lateinit var fragmentProductInfoBinding: FragmentProductInfoBinding
    lateinit var mainActivity: MainActivity

    // 임시 데이터
    val list: ArrayList<Int> = ArrayList<Int>().let {
        it.apply {
            add(R.drawable.img_dog_food1)
            add(R.drawable.img_dog_food2)
            add(R.drawable.img_dog_food3)
        }
    }

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

            // 상세 이미지1
            imageviewProductinfoDetail1.run {
                setImageResource(R.drawable.img_dog_food_detail)
            }

            // 찜 버튼
            imageviewProductinfoHeart.run{
                var flag = false
                setOnClickListener {
                    // 찜한 상태일 때
                    if(flag){
                        this.setImageResource(R.drawable.ic_favorite_48dp)
                    }
                    // 찜하지 않은 상태일 때
                    else {
                        this.setImageResource(R.drawable.ic_favorite_fill_48dp)
                    }

                    flag = !flag
                }
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
    inner class ProductInfoFragmentStateAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
        // 보여줄 페이지 수
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ProductInfoViewPagerFragment(list[position])
                1 -> ProductInfoViewPagerFragment(list[position])
                else -> ProductInfoViewPagerFragment(list[position])
            }
        }
    }
}