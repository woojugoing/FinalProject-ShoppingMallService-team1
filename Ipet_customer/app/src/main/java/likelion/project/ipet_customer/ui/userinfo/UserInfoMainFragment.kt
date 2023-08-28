package likelion.project.ipet_customer.ui.userinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentUserInfoMainBinding
import likelion.project.ipet_customer.databinding.ItemUserinfoChangeaddressBinding
import likelion.project.ipet_customer.databinding.ItemUserinfoDepositBinding
import likelion.project.ipet_customer.databinding.ItemUserinfoDrawelcheckBinding
import likelion.project.ipet_customer.ui.login.LoginViewModel
import likelion.project.ipet_customer.ui.main.MainActivity

class UserInfoMainFragment : Fragment() {

    lateinit var fragmentUserInfoMainBinding: FragmentUserInfoMainBinding
    lateinit var mainActivity: MainActivity
    var fragmentState = ""
    lateinit var data: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentUserInfoMainBinding = FragmentUserInfoMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        Log.i("user", LoginViewModel.customer.customerId)
        Log.i("user", LoginViewModel.customer.customerName)
        Log.i("user", LoginViewModel.customer.customerEmail)
        Log.i("user", LoginViewModel.customer.customerAddress)


        fragmentUserInfoMainBinding.run {
            data = arguments?.getString("data") ?: ""
            textViewUserInfoAddress.append(data)

            toolbarUserInfoMain.run {
                title = "내 정보"
                setNavigationIcon(R.drawable.ic_back_24dp)
                setNavigationOnClickListener {
                    mainActivity.replaceFragment(MainActivity.PRODUCT_INFO_FRAGMENT, false, null)
                }
            }

            textViewUserInfoName.run{
                text = LoginViewModel.customer.customerName + "님"
            }

            textViewUserInfoEmail.run{
                text = LoginViewModel.customer.customerEmail
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
                fragmentState = "Shopping"
                val newBundle = Bundle()
                newBundle.putString("fragmentState", fragmentState)
                mainActivity.replaceFragment(MainActivity.SHOPPING_BASKET_FRAGMENT, true, newBundle)
            }

            imageViewUserInfoCoupon.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.COUPON_FRAGMENT, true, null)
            }

            imageViewUserInfoFavorite.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.HEART_FRAGMENT, true, null)
            }

            layoutUserInfoLogout.setOnClickListener {
                mainActivity.activityMainBinding.bottomNavigation.visibility = View.GONE
                mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, null)
            }

            layoutUserInfoChangeAddress.setOnClickListener {
                val binding = ItemUserinfoChangeaddressBinding.inflate(LayoutInflater.from(context))
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setView(binding.root)
                val dialog = builder.create()
                binding.textViewChangeAddressAddress.text = data

                binding.buttonChangeAddressAdd.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.USER_INFO_ADDRESS_FRAGMENT, true, null)
                    dialog.dismiss()
                }

                binding.buttonChangeAddressConfirm.setOnClickListener {
                    data += " ${binding.editTextChangeAddressDetail.text}"
                    val address = data
                    Log.d("woojugoing", address)
                    Toast.makeText(mainActivity, "상세 주소가 정상적으로 저장되었습니다.", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

                if(data == "") {
                    binding.textViewChangeAddressAddress.visibility = View.GONE
                    binding.editTextChangeAddressDetail.visibility = View.GONE
                    binding.buttonChangeAddressConfirm.visibility = View.GONE
                }
                dialog.show()
            }

            layoutUserInfoWithDrawal.setOnClickListener {
                val binding = ItemUserinfoDrawelcheckBinding.inflate(LayoutInflater.from(context))
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setView(binding.root)
                val dialog = builder.create()

                binding.buttonDrawelCheckYes.setOnClickListener {
                    mainActivity.activityMainBinding.bottomNavigation.visibility = View.GONE
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