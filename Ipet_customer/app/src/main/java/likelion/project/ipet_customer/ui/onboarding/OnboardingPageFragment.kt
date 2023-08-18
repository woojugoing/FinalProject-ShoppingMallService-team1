package likelion.project.ipet_customer.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import likelion.project.ipet_customer.databinding.FragmentOnboardingPageBinding

const val ARG_PARM1 = "param1"
const val ARG_PARM2 = "param2"
const val ARG_PARM3 = "param3"

class OnBoardingPageFragment : Fragment() {

    lateinit var onboardingPageBinding: FragmentOnboardingPageBinding
    private var descripts: Array<String>? = null
    private var lottieResIds: IntArray? = null
    private var page: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onboardingPageBinding = FragmentOnboardingPageBinding.inflate(inflater)
        arguments?.let {
            descripts = it.getStringArray(ARG_PARM1)
            lottieResIds = it.getIntArray(ARG_PARM2)
            page = it.getInt(ARG_PARM3)
        }
        setOnboarding()
        return onboardingPageBinding.root
    }

    private fun setOnboarding() {
        onboardingPageBinding.run {
            when (page) {
                OnboardingPage.FIRST_PAGE.position -> showFirstPage()
                OnboardingPage.SECOND_PAGE.position -> showSecondPage()
                OnboardingPage.THIRD_PAGE.position -> showThirdPage()
                else -> showFourthPage()
            }
        }
    }

    private fun showFirstPage() {
        onboardingPageBinding.run {
            lottieAnimationViewOnboardingpageFirst.apply {
                lottieResIds?.let {
                    setAnimation(it[0])
                }
                playAnimation()
            }
            descripts?.let {
                textViewOnboardingpageFirstDescript1.text = it[0]
                textViewOnboardingpageFirstDescript2.text = it[1]
            }
        }
    }

    private fun showSecondPage() {
        onboardingPageBinding.run {
            lottieAnimationViewOnboardingpageSecond.apply {
                lottieResIds?.let {
                    setAnimation(it[0])
                }
                playAnimation()
            }
            descripts?.let {
                textViewOnboardingpageSecondDescript1.text = it[0]
                textViewOnboardingpageSecondDescript2.text = it[1]
                textViewOnboardingpageSecondDescript3.text = it[2]
            }
        }
    }

    private fun showThirdPage() {
        onboardingPageBinding.run {
            lottieAnimationViewOnboardingpageThirdCat.apply {
                lottieResIds?.let {
                    setAnimation(it[0])
                }
                playAnimation()
            }
            lottieAnimationViewOnboardingpageThirdDog.apply {
                lottieResIds?.let {
                    setAnimation(it[1])
                }
                playAnimation()
            }
            descripts?.let {
                textViewOnboardingpageThirdDescript1.text = it[0]
                textViewOnboardingpageThirdDescript2.text = it[1]
                textViewOnboardingpageThirdDescript3.text = it[2]
                textViewOnboardingpageThirdDescript4.text = it[3]
            }
        }
    }

    private fun showFourthPage() {
        onboardingPageBinding.run {
            lottieAnimationViewOnboardingpageFourth.apply {
                lottieResIds?.let {
                    setAnimation(it[0])
                }
                playAnimation()
            }
            descripts?.let {
                textViewOnboardingpageFourthDescript1.text = it[0]
                textViewOnboardingpageFourthDescript2.text = it[1]
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(descripts: Array<String>, lottieResIds: IntArray, page: Int): Fragment {
            return OnBoardingPageFragment().apply {
                arguments = Bundle().apply {
                    putStringArray(ARG_PARM1, descripts)
                    putIntArray(ARG_PARM2, lottieResIds)
                    putInt(ARG_PARM3, page)
                }
            }
        }
    }
}