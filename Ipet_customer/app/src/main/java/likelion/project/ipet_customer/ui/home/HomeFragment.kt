package likelion.project.ipet_customer.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentHomeBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class HomeFragment : Fragment() {

    lateinit var fragmentHomeBinding: FragmentHomeBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: HomeViewModel
    var lCategoryState: String = "사료"
    var sCategoryState: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.run {
            jointsLiveData.observe(viewLifecycleOwner) { joints ->
                fragmentHomeBinding.recyclerViewHomeJoint.adapter = HomeJointAdapter(mainActivity, joints)
            }

            productLiveData.observe(viewLifecycleOwner){ products ->
                fragmentHomeBinding.recyclerViewHomeBest.adapter = HomeBestAdapter(mainActivity, products)
            }

            loadFilteredJoints()
            loadFilteredOrder()
        }

        fragmentHomeBinding.run {
            mainActivity.activityMainBinding.bottomNavigation.visibility = View.VISIBLE
            // 시작 시 강아지 버튼 선택으로 지정하여 색상 변경
            buttonHomeDog.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.rose_200))

            // 좌측 상단 toggle button
            toggleButtonHome.run {
                addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
                    if (isChecked) {
                        when (checkedId) {
                            // 강아지 버튼 클릭 시
                            R.id.button_home_dog -> {

                                viewModel.updateFilter("강아지")

                                buttonHomeDog.setBackgroundColor(
                                    ContextCompat.getColor(
                                        mainActivity,
                                        R.color.rose_200
                                    )
                                )
                                buttonHomeCat.setBackgroundColor(
                                    ContextCompat.getColor(
                                        mainActivity,
                                        R.color.white
                                    )
                                )


                            }

                            // 고양이 버튼 클릭 시
                            R.id.button_home_cat -> {

                                viewModel.updateFilter("고양이")

                                buttonHomeCat.setBackgroundColor(
                                    ContextCompat.getColor(
                                        mainActivity,
                                        R.color.rose_200
                                    )
                                )
                                buttonHomeDog.setBackgroundColor(
                                    ContextCompat.getColor(
                                        mainActivity,
                                        R.color.white
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // toolbar
            toolbarHome.run {
                setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.item_search -> {
                            mainActivity.replaceFragment(MainActivity.SEARCH_MAIN_FRAGMENT, true, null)
                        }
                    }

                    false
                }
            }

            // 대분류 카테고리 선택 시 UI 변경
            chipGroupHomeLcategory.run {
                setOnCheckedStateChangeListener { group, checkedIds ->
                    // test
                    when (this.checkedChipId) {
                        R.id.chip_home_food -> {
                            lCategoryState = "사료"
                            setSmallCategory("주니어", "어덜트", "시니어", "다이어트", "건식", "습식")
                        }

                        R.id.chip_home_snack -> {
                            lCategoryState = "간식"
                            setSmallCategory("껌", "스낵", "육포", "캔", "비스켓", "기타")
                        }

                        R.id.chip_home_toy -> {
                            lCategoryState = "장난감"
                            setSmallCategory("공", "인형", "큐브", "훈련용품", "스크래쳐", "기타")
                        }

                        R.id.chip_home_clothes -> {
                            lCategoryState = "의류"
                            setSmallCategory("레인코트", "신발", "외투", "원피스", "셔츠", "기타")
                        }

                        R.id.chip_home_house -> {
                            lCategoryState = "집"
                            setSmallCategory("계단", "매트", "울타리", "안전문", "하우스", "기타")
                        }
                    }
                }
            }

            buttonHomeScategory1.setOnClickListener {replaceToList(1)}
            buttonHomeScategory2.setOnClickListener {replaceToList(2)}
            buttonHomeScategory3.setOnClickListener {replaceToList(3)}
            buttonHomeScategory4.setOnClickListener {replaceToList(4)}
            buttonHomeScategory5.setOnClickListener {replaceToList(5)}
            buttonHomeScategory6.setOnClickListener {replaceToList(6)}

            // 공동 구매 상품 더보기 클릭 시
            textViewHomeMore.setOnClickListener {
                val newBundle = Bundle()
                newBundle.putBoolean("menuFlag", false)

                mainActivity.replaceFragment(MainActivity.PRODUCT_JOINT_LIST_FRAGMENT, true, newBundle)
            }
        }

        return fragmentHomeBinding.root
    }

    fun replaceToList(state: Int){
        sCategoryState = state
        val newBundle = Bundle()
        newBundle.putString("lCategoryState", lCategoryState)
        newBundle.putInt("sCategoryState", sCategoryState)
        mainActivity.replaceFragment(MainActivity.PRODUCT_LIST_FRAGMENT, true, newBundle)
    }

    fun setSmallCategory(cat1: String, cat2: String, cat3: String, cat4: String, cat5: String, cat6: String) {
        fragmentHomeBinding.buttonHomeScategory1.text = cat1
        fragmentHomeBinding.buttonHomeScategory2.text = cat2
        fragmentHomeBinding.buttonHomeScategory3.text = cat3
        fragmentHomeBinding.buttonHomeScategory4.text = cat4
        fragmentHomeBinding.buttonHomeScategory5.text = cat5
        fragmentHomeBinding.buttonHomeScategory6.text = cat6
    }
}