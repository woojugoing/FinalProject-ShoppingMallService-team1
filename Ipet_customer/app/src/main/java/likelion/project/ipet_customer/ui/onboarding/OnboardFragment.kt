package likelion.project.ipet_customer.ui.onboarding


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentOnboardingBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class OnboardFragment : Fragment() {

    lateinit var fragmentOnboardBinding: FragmentOnboardingBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: OnboardingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentOnboardBinding = FragmentOnboardingBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, OnboardingViewModelFactory(mainActivity))[OnboardingViewModel::class.java]
        initViewPager()
        initEvent()
        return fragmentOnboardBinding.root
    }

    private fun initEvent() {
        setViewPagePosition()
        setButtonTextToPageTransition()
    }

    private fun setButtonTextToPageTransition() {
        fragmentOnboardBinding.run {
            viewpagerOnboarding.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        OnboardingPage.FOURTH_PAGE.position -> buttonOnboardingNext.text =
                            getString(R.string.start)
                        else -> buttonOnboardingNext.text = getString(R.string.next)
                    }
                }
            })
        }
    }

    private fun setViewPagePosition() {
        fragmentOnboardBinding.run {
            buttonOnboardingNext.setOnClickListener {
                viewpagerOnboarding.run {
                    if (currentItem == END_PAGE) {
                        viewModel.writeFirstVisitor()
                        mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, null)
                    } else {
                        currentItem += 1
                    }
                }
            }
        }
    }

    private fun initViewPager() {
        fragmentOnboardBinding.run {
            viewpagerOnboarding.adapter = ScreenSlidePagerAdapter(this@OnboardFragment)
            viewpagerOnboarding.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewpagerOnboarding.isUserInputEnabled = false

            TabLayoutMediator(
                tabLayoutOnboardingDot,
                viewpagerOnboarding
            ) { tab, position -> }.attach()
        }
    }

    private inner class ScreenSlidePagerAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = PAGES_NUMBER
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                OnboardingPage.FIRST_PAGE.position -> {
                    OnBoardingPageFragment.newInstance(
                        descripts = arrayOf("아이펫", "반려동물 용품의 중심"),
                        lottieResIds = intArrayOf(R.raw.raw_shopping_bag),
                        page = OnboardingPage.FIRST_PAGE.position
                    )
                }

                OnboardingPage.SECOND_PAGE.position -> {
                    OnBoardingPageFragment.newInstance(
                        descripts = arrayOf("함께 사면 더 저렴", "공동구매로 할인 혜택을", "받아보세요."),
                        lottieResIds = intArrayOf(R.raw.raw_sale),
                        page = OnboardingPage.SECOND_PAGE.position
                    )
                }

                OnboardingPage.THIRD_PAGE.position -> {
                    OnBoardingPageFragment.newInstance(
                        descripts = arrayOf("소중한", "우리 가족 아무거나", "먹일 수 없으니까", "전 제품 직접 검수"),
                        lottieResIds = intArrayOf(R.raw.raw_cat, R.raw.raw_dog),
                        page = OnboardingPage.THIRD_PAGE.position
                    )
                }

                else -> {
                    OnBoardingPageFragment.newInstance(
                        descripts = arrayOf("평일 14시 이전 주문 시", "당일 배송"),
                        lottieResIds = intArrayOf(R.raw.raw_deliver_box),
                        page = OnboardingPage.FOURTH_PAGE.position
                    )
                }
            }
        }
    }

    companion object {
        private const val PAGES_NUMBER = 4
        private const val END_PAGE = 3
    }
}

enum class OnboardingPage(val position: Int) {
    FIRST_PAGE(0),
    SECOND_PAGE(1),
    THIRD_PAGE(2),
    FOURTH_PAGE(3)
}
