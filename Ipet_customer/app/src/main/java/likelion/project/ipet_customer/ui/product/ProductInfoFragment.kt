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
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentProductInfoBinding
import likelion.project.ipet_customer.model.Joint
import likelion.project.ipet_customer.model.Product
import likelion.project.ipet_customer.ui.main.MainActivity

class ProductInfoFragment : Fragment() {

    lateinit var binding: FragmentProductInfoBinding
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
        binding = FragmentProductInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        readToggle = arguments?.getString("readToggle")!!

        if (readToggle == "product"){
            readProductIdx = arguments?.getString("readProductIdx")!!
        } else {
            readJointIdx = arguments?.getLong("readJointIdx")!!
        }

        productInfoViewModel = ViewModelProvider(this)[ProductInfoViewModel::class.java]

        binding.run {

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
            imageviewProductinfoDetail1.setImageResource(R.drawable.img_dog_food_detail)

            // 찜 버튼
            imageviewProductinfoHeart.setOnClickListener {
                imageviewProductinfoHeart.setImageResource(
                    if (it.isSelected) R.drawable.ic_favorite_48dp
                    else R.drawable.ic_favorite_fill_48dp
                )
                it.isSelected = !it.isSelected
            }

            textviewProductinfoReviewnumber.setOnClickListener {
                scrollviewProductinfo.fullScroll(ScrollView.FOCUS_DOWN)
            }

            // 리뷰 보러 가기 버튼
            buttonProductInfoShowReview.setOnClickListener {
                readProductIdx = arguments?.getString("readProductIdx")!!
                val newBundle = Bundle()
                newBundle.putString("readProductIdx", readProductIdx)
                mainActivity.replaceFragment(MainActivity.REVIEWALL_FRAGMENT, true, newBundle)
            }

            // 구매하기 버튼
            buttonProductinfoBuy.setOnClickListener {
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
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

        setupViewModel()
        return binding.root
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

    private fun setupViewModel() {
        if (readToggle == "product") {
            productInfoViewModel.productLiveData.observe(viewLifecycleOwner) { product ->
                handleProductData(product)
            }
            productInfoViewModel.loadOneProduct(readProductIdx)
        } else {
            productInfoViewModel.jointLiveData.observe(viewLifecycleOwner) { joint ->
                handleJointData(joint)
            }
            productInfoViewModel.loadOneJoint(readJointIdx)
        }
    }

    private fun handleProductData(product: Product) {
        loadText(product.productTitle, product.productText, product.productPrice)
        imgList = product.productImg as ArrayList<String>
        binding.viewpager2ProductinfoThumbnail.adapter = ProductInfoFragmentStateAdapter(mainActivity)
        loadDetailImage()
    }

    private fun handleJointData(joint: Joint) {
        loadText(joint.jointTitle, joint.jointText, joint.jointPrice)
        imgList = joint.jointImg as ArrayList<String>
        binding.viewpager2ProductinfoThumbnail.adapter = ProductInfoFragmentStateAdapter(mainActivity)
        loadDetailImage()
    }

    fun loadDetailImage(){
        if (imgList.size > 3){
            Glide.with(requireContext())
                .load(imgList[3])
                .into(binding.imageviewProductinfoDetail1)

            Glide.with(requireContext())
                .load(imgList[4])
                .into(binding.imageviewProductinfoDetail2)
        }
    }

    fun loadText(title:String, text:String, price:Long){
        binding.textviewProductinfoTitle.text = title
        binding.textviewProductinfoText.text = text
        binding.textviewProductinfoPrice.text = "${mainActivity.formatNumberToCurrency(price)}원"
    }
}