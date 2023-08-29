package likelion.project.ipet_customer.ui.product

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentProductInfoBinding
import likelion.project.ipet_customer.model.Joint
import likelion.project.ipet_customer.model.Product
import likelion.project.ipet_customer.model.Review
import likelion.project.ipet_customer.ui.main.MainActivity

class ProductInfoFragment : Fragment() {

    lateinit var binding: FragmentProductInfoBinding
    lateinit var mainActivity: MainActivity
    lateinit var productInfoViewModel: ProductInfoViewModel

    var readProductIdx = ""
    var readJointIdx = ""
    var readToggle = ""

    var imgList: List<String> = emptyList()
    lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        sheetBehavior = BottomSheetBehavior.from(binding.includeProductinfoBottomsheet.bottomsheetProductinfo)

        readToggle = arguments?.getString("readToggle")!!

        if (readToggle == "product") {
            readProductIdx = arguments?.getString("readProductIdx")!!
        } else {
            readJointIdx = arguments?.getString("readJointIdx")!!
        }

        productInfoViewModel = ViewModelProvider(this)[ProductInfoViewModel::class.java]
        
        setupHeartListener()

        binding.run {
            toolbarProductInfo.run {

                setNavigationIcon(R.drawable.ic_back_24dp)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.PRODUCT_INFO_FRAGMENT)
                }
            }

            // 원가 가격 표시
            textviewProductinfoCostprice.paintFlags =
                textviewProductinfoCostprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            // 상세 이미지1
            imageviewProductinfoDetail1.setImageResource(R.drawable.img_dog_food_detail)

            textviewProductinfoReviewnumber.setOnClickListener {
                scrollviewProductinfo.fullScroll(ScrollView.FOCUS_DOWN)
            }

            // 리뷰 보러 가기 버튼
            buttonProductInfoShowReview.setOnClickListener {
                readToggle = arguments?.getString("readToggle")!!

                if (readToggle == "product"){
                    readProductIdx = arguments?.getString("readProductIdx")!!
                    val newBundle = Bundle()
                    newBundle.putString("readProductIdx", readProductIdx)
                    mainActivity.replaceFragment(MainActivity.REVIEWALL_FRAGMENT, true, newBundle)
                } else {
                    readJointIdx = arguments?.getString("readJointIdx")!!
                    val newBundle = Bundle()
                    newBundle.putString("readProductIdx", readJointIdx)
                    mainActivity.replaceFragment(MainActivity.REVIEWALL_FRAGMENT, true, newBundle)
                }
            }

            sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    Log.d("InfoFragment", "BottomSheet state changed: $newState")
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // 슬라이딩 중인 경우
                }
            })

            setupViewModel()
            return binding.root
        }
    }

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
            productInfoViewModel.reviewLiveData.observe(viewLifecycleOwner){
                handleReviewData(it)
            }
            productInfoViewModel.loadOneProduct(readProductIdx)
            productInfoViewModel.loadSelectReview(readProductIdx)
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
        binding.run {
            viewpager2ProductinfoThumbnail.adapter = ProductInfoFragmentStateAdapter(mainActivity)

            buttonProductinfoBuy.setOnClickListener {
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

            }

            includeProductinfoBottomsheet.run {

                // 원가 가격 표시
                textviewBottomsheetCostprice.paintFlags =
                    textviewProductinfoCostprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                var num = 1
                // 왼쪽 화살표 버튼
                imagebuttonBottomsheetMinus.setOnClickListener {
                    if (num > 1) {
                        num--
                        textviewBottomsheetNumber.text = "$num"
                    }
                }
                // 오른쪽 화살표 버튼
                imagebuttonBottomsheetPlus.setOnClickListener {
                    num++
                    textviewBottomsheetNumber.text = "$num"
                }

                buttonBottomsheetBuy.setOnClickListener {
                    lifecycleScope.launch {
                        for (n in 1 .. num) {
                            productInfoViewModel.setAddCart(readProductIdx, "일반")
                        }
                    }

                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    mainActivity.replaceFragment(MainActivity.SHOPPING_BASKET_FRAGMENT, true, null)
                }

                buttonBottomsheetCart.setOnClickListener {
                    if (sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    }

                    lifecycleScope.launch {
                        for (n in 1 .. num) {
                            productInfoViewModel.setAddCart(readProductIdx, "일반")
                        }
                    }
                }
            }
        }

        setupTabLayoutMediator()
        loadDetailImage()
    }

    private fun handleJointData(joint: Joint) {
        loadText(joint.jointTitle, joint.jointText, joint.jointPrice)
        imgList = joint.jointImg as ArrayList<String>
        binding.run {
            viewpager2ProductinfoThumbnail.adapter = ProductInfoFragmentStateAdapter(mainActivity)
            buttonProductinfoBuy.run {
                text = "공동 구매 참여하기"
                setOnClickListener {
                    productInfoViewModel.setAddCart(readJointIdx, "공동")
                    mainActivity.replaceFragment(MainActivity.SHOPPING_BASKET_FRAGMENT, true, null)
                }
            }

            imageviewProductinfoHeart.visibility = View.GONE
        }

        setupTabLayoutMediator()
        loadDetailImage()
    }

    private fun handleReviewData(reviews: MutableList<Review>) {
        if (reviews.isEmpty()){
            loadReviewData(0f, 0)
        } else {
            var total = 0f

            for (review in reviews){
                total += review.reviewScore.toFloat()
            }

            loadReviewData(total/reviews.size, reviews.size)
        }
    }

    private fun setupHeartListener() {
        val productIdx = readProductIdx

        productInfoViewModel.registerHeartListener(productIdx) { isHearted ->
            // 리스너 콜백에서 하트 버튼 상태와 동작을 업데이트
            if (isHearted) {
                binding.imageviewProductinfoHeart.setImageResource(R.drawable.ic_favorite_fill_48dp)
                binding.imageviewProductinfoHeart.setOnClickListener {
                    productInfoViewModel.setDeleteHeart(readProductIdx)
                }
            } else {
                binding.imageviewProductinfoHeart.setImageResource(R.drawable.ic_favorite_48dp)
                binding.imageviewProductinfoHeart.setOnClickListener {
                    productInfoViewModel.setAddHeart(readProductIdx)
                }
            }
        }
    }

    fun setupTabLayoutMediator(){
        TabLayoutMediator(
            binding.tabLayoutProductinfoDot,
            binding.viewpager2ProductinfoThumbnail
        ) { tab, position -> }.attach()
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

    fun loadReviewData(score:Float, number : Int){
        binding.run {
            ratingbarProductinfoScore.rating = score
            textviewProductinfoScore.text = "$score"
            textviewProductinfoReviewnumber.text = "($number)"
        }
    }
}