package likelion.project.ipet_customer.ui.home

import android.os.Bundle
import android.util.Log
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
    private var lCategoryState: String = "사료"
    private var sCategoryState: Int = 0
    lateinit var viewModel: HomeViewModel

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

            loadAllJoints()
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
                buttonHomeScategory6.visibility = View.INVISIBLE

                setOnCheckedStateChangeListener { group, checkedIds ->
                    // test
                    when (this.checkedChipId) {
                        R.id.chip_home_food -> {
                            lCategoryState = "사료"
                        }

                        R.id.chip_home_snack -> {
                            lCategoryState = "간식"
                        }

                        R.id.chip_home_toy -> {
                            lCategoryState = "장난감"
                        }

                        R.id.chip_home_clothes -> {
                            lCategoryState = "의류"
                        }

                        R.id.chip_home_house -> {
                            lCategoryState = "집"
                        }
                    }
                }
            }

            buttonHomeScategory1.setOnClickListener {
                sCategoryState = 1
                val newBundle = Bundle()
                newBundle.putString("lCategoryState", lCategoryState)
                newBundle.putInt("sCategoryState", sCategoryState)
                Log.d("sCs", "$sCategoryState , $lCategoryState")
                mainActivity.replaceFragment(MainActivity.PRODUCT_LIST_FRAGMENT, true, newBundle)
            }
            buttonHomeScategory2.setOnClickListener {
                sCategoryState = 2
                val newBundle = Bundle()
                newBundle.putString("lCategoryState", lCategoryState)
                newBundle.putInt("sCategoryState", sCategoryState)
                Log.d("sCs", "$sCategoryState , $lCategoryState")
                mainActivity.replaceFragment(MainActivity.PRODUCT_LIST_FRAGMENT, true, newBundle)
            }
            buttonHomeScategory3.setOnClickListener {
                sCategoryState = 3
                val newBundle = Bundle()
                newBundle.putString("lCategoryState", lCategoryState)
                newBundle.putInt("sCategoryState", sCategoryState)
                Log.d("sCs", "$sCategoryState , $lCategoryState")
                mainActivity.replaceFragment(MainActivity.PRODUCT_LIST_FRAGMENT, true, newBundle)
            }
            buttonHomeScategory4.setOnClickListener {
                sCategoryState = 4
                val newBundle = Bundle()
                newBundle.putString("lCategoryState", lCategoryState)
                newBundle.putInt("sCategoryState", sCategoryState)
                Log.d("sCs", "$sCategoryState , $lCategoryState")
                mainActivity.replaceFragment(MainActivity.PRODUCT_LIST_FRAGMENT, true, newBundle)
            }
            buttonHomeScategory5.setOnClickListener {
                sCategoryState = 5
                val newBundle = Bundle()
                newBundle.putString("lCategoryState", lCategoryState)
                newBundle.putInt("sCategoryState", sCategoryState)
                Log.d("sCs", "$sCategoryState , $lCategoryState")
                mainActivity.replaceFragment(MainActivity.PRODUCT_LIST_FRAGMENT, true, newBundle)
            }
            buttonHomeScategory6.setOnClickListener {
                sCategoryState = 6
                val newBundle = Bundle()
                newBundle.putString("lCategoryState", lCategoryState)
                newBundle.putInt("sCategoryState", sCategoryState)
                Log.d("sCs", "$sCategoryState , $lCategoryState")
                mainActivity.replaceFragment(MainActivity.PRODUCT_LIST_FRAGMENT, true, newBundle)
            }

            // 공동 구매 상품 더보기 클릭 시
            textViewHomeMore.setOnClickListener {
                val newBundle = Bundle()
                newBundle.putBoolean("menuFlag", false)

                mainActivity.replaceFragment(MainActivity.PRODUCT_LIST_FRAGMENT, true, newBundle)
            }

            recyclerViewHomeJoint.run {
                val jointsList = viewModel.jointsLiveData.value?.toMutableList() ?: mutableListOf()
                adapter = HomeJointAdapter(mainActivity, jointsList)
            }

            recyclerViewHomeBest.run {
                adapter = HomeBestAdapter(mainActivity)
            }
        }

        return fragmentHomeBinding.root
    }
}