package likelion.project.ipet_customer.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import likelion.project.ipet_customer.databinding.FragmentPaymentCompleteBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class PaymentCompleteFragment : Fragment() {

    lateinit var fragmentPaymentCompleteBinding: FragmentPaymentCompleteBinding
    lateinit var mainActivity: MainActivity
    lateinit var callback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뒤로가기 버튼을 막기 위한 callback을 생성하고 등록한다
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 여기서 아무것도 하지 않으면 뒤로가기 동작이 막힌다.
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentPaymentCompleteBinding = FragmentPaymentCompleteBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentPaymentCompleteBinding.run {

            lottieAnimationViewPaymentComplete.run {
                playAnimation()
            }

            // 홈 화면으로 이동
            buttonPaymentCompleteGoMain.run {
                setOnClickListener {

                    val fragmentManager = mainActivity.supportFragmentManager
                    // 모든 백스택 항목 제거
                    for (i in 0 until fragmentManager.backStackEntryCount) {
                        fragmentManager.popBackStackImmediate()
                    }
                    mainActivity.replaceFragment(MainActivity.HOME_FRAGMENT, false, null)

                }
            }

            // 주문확인 하기 (내정보 화면으로 이동)
            buttonPaymentCompleteGoOrder.run {
                setOnClickListener {

                    val fragmentManager = mainActivity.supportFragmentManager
                    // 모든 백스택 항목 제거
                    for (i in 0 until fragmentManager.backStackEntryCount) {
                        fragmentManager.popBackStackImmediate()
                    }

                    mainActivity.replaceFragment(MainActivity.USER_INFO_MAIN_FRAGMENT, false, null)
                }
            }

        }

        return fragmentPaymentCompleteBinding.root
    }
}