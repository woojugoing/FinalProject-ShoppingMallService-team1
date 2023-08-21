package likelion.project.ipet_customer.ui.userinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentUserInfoMainBinding
import likelion.project.ipet_customer.databinding.ItemUserinfoDepositBinding
import likelion.project.ipet_customer.databinding.ItemUserinfoDrawelcheckBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class UserInfoMainFragment : Fragment() {

    lateinit var fragmentUserInfoMainBinding: FragmentUserInfoMainBinding
    lateinit var mainActivity: MainActivity
    var fragmentState = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentUserInfoMainBinding = FragmentUserInfoMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        fragmentUserInfoMainBinding.run {
            toolbarUserInfoMain.run {
                title = "내 정보"
                setNavigationIcon(R.drawable.ic_back_24dp)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.USER_INFO_MAIN_FRAGMENT)
                }
            }

            imageViewUserInfoCancel.setOnClickListener {
                fragmentState = "Cancel"
                val newBundle = Bundle()
                newBundle.putString("fragmentState", fragmentState)
                mainActivity.replaceFragment(MainActivity.ORDER_STATUS_FRAGMENT, true, newBundle)
            }

            textViewUserInfoDetails.setOnClickListener {
                fragmentState = "Detail"
                val newBundle = Bundle()
                newBundle.putString("fragmentState", fragmentState)
                mainActivity.replaceFragment(MainActivity.ORDER_STATUS_FRAGMENT, true, newBundle)
            }

            imageViewUserInfoReview.setOnClickListener {
                fragmentState = "Review"
                val newBundle = Bundle()
                newBundle.putString("fragmentState", fragmentState)
                mainActivity.replaceFragment(MainActivity.ORDER_STATUS_FRAGMENT, true, newBundle)
            }

            imageViewUserInfoShopBasket.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.SHOPPING_BASKET_FRAGMENT, true, null)
            }

            imageViewUserInfoCoupon.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.COUPON_FRAGMENT, true, null)
            }

            imageViewUserInfoFavorite.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.HEART_FRAGMENT, true, null)
            }

            layoutUserInfoLogout.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, null)
            }

            textViewUserInfoDeposit.setOnClickListener {
                val binding = ItemUserinfoDepositBinding.inflate(LayoutInflater.from(context))
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setView(binding.root)
                binding.textViewUserinfoDeposit.append("[2023/02/11]\t\t(2,000)\t\t고양이 사료\n")
                binding.textViewUserinfoDeposit.append("[2023/03/13]\t\t(3,000)\t\t강아지 장난감\n")

                builder.show()
            }

            layoutUserInfoChangeAddress.setOnClickListener {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                    .setView(R.layout.item_userinfo_changeaddress)
                    .setPositiveButton("확인", null)
                    .setNegativeButton("취소", null)
                builder.show()
            }

            layoutUserInfoWithDrawal.setOnClickListener {
                val binding = ItemUserinfoDrawelcheckBinding.inflate(LayoutInflater.from(context))
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setView(binding.root)

                val dialog = builder.create()

                binding.buttonDrawelCheckYes.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, null)
                    dialog.dismiss()
                }

                binding.buttonDrawelCheckNo.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }

        }

        return fragmentUserInfoMainBinding.root
    }
}