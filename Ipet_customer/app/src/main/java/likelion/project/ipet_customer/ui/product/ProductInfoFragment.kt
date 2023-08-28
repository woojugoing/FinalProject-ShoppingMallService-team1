package likelion.project.ipet_customer.ui.product

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentProductInfoBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class ProductInfoFragment : Fragment() {

    lateinit var fragmentProductInfoBinding: FragmentProductInfoBinding
    lateinit var mainActivity: MainActivity
    lateinit var productInfoViewModel: ProductInfoViewModel

    var readProductIdx = ""
    var readJointIdx = 0L
    var readToggle = ""
    
    var imgList: List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductInfoBinding = FragmentProductInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        readToggle = arguments?.getString("readToggle")!!

        if (readToggle == "product"){
            readProductIdx = arguments?.getString("readProductIdx")!!
        } else {
            readJointIdx = arguments?.getLong("readJointIdx")!!
        }

        productInfoViewModel = ViewModelProvider(this)[ProductInfoViewModel::class.java]

        productInfoViewModel.run {
            if (readToggle == "product"){
                productLiveData.observe(viewLifecycleOwner){
                    fragmentProductInfoBinding.textviewProductinfoTitle.text = it.productTitle
                    fragmentProductInfoBinding.textviewProductinfoText.text = it.productText
                    fragmentProductInfoBinding.textviewProductinfoPrice.text = "${mainActivity.formatNumberToCurrency(it.productPrice)}원"
                    imgList = it.productImg as ArrayList<String>
                    fragmentProductInfoBinding.viewpager2ProductinfoThumbnail.adapter = ProductInfoFragmentStateAdapter(mainActivity)
                }
            } else {
                jointLiveData.observe(viewLifecycleOwner){
                    fragmentProductInfoBinding.textviewProductinfoTitle.text = it.jointTitle
                    fragmentProductInfoBinding.textviewProductinfoText.text = it.jointText
                    fragmentProductInfoBinding.textviewProductinfoPrice.text = "${mainActivity.formatNumberToCurrency(it.jointPrice)}원"
                    imgList = it.jointImg as ArrayList<String>
                    fragmentProductInfoBinding.viewpager2ProductinfoThumbnail.adapter = ProductInfoFragmentStateAdapter(mainActivity)
                }
            }
        }

        fragmentProductInfoBinding.run {

            val sheetBehavior = BottomSheetBehavior.from(includeProductinfoBottomsheet.bottomsheetProductinfo)

            toolbarProductInfo.run {

                setNavigationIcon(R.drawable.ic_back_24dp)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.PRODUCT_INFO_FRAGMENT)
                }
            }

            // 원가 가격 표시
            textviewProductinfoCostprice.paintFlags = textviewProductinfoCostprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

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

            textviewProductinfoReviewnumber.run {
                setOnClickListener {
                    scrollviewProductinfo.fullScroll(ScrollView.FOCUS_DOWN)
                }
            }

            // 리뷰 보러 가기 버튼
            buttonProductInfoShowReview.run {
                setOnClickListener {
                    readToggle = arguments?.getString("readToggle")!!

                    if (readToggle == "product"){
                        readProductIdx = arguments?.getString("readProductIdx")!!
                        val newBundle = Bundle()
                        newBundle.putString("readProductIdx", readProductIdx)
                        mainActivity.replaceFragment(MainActivity.REVIEWALL_FRAGMENT, true, newBundle)
                    } else {
                        readJointIdx = arguments?.getLong("readJointIdx")!!
                        val newBundle = Bundle()
                        newBundle.putString("readProductIdx", readJointIdx.toString())
                        mainActivity.replaceFragment(MainActivity.REVIEWALL_FRAGMENT, true, newBundle)
                    }
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

        if (readToggle == "product"){
            productInfoViewModel.loadOneProduct(readProductIdx)
        } else {
            productInfoViewModel.loadOneJoint(readJointIdx)
        }

        return fragmentProductInfoBinding.root
    }

    // viewPager2 Adapter
    inner class ProductInfoFragmentStateAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
        // 보여줄 페이지 수
        override fun getItemCount(): Int = minOf(imgList.size, 3)

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ProductInfoViewPagerFragment(imgList[position])
                1 -> ProductInfoViewPagerFragment(imgList[position])
                else -> ProductInfoViewPagerFragment(imgList[position])
            }
        }
    }
}