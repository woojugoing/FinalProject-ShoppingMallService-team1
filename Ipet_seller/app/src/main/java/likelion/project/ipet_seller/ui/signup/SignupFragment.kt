package likelion.project.ipet_seller.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.databinding.FragmentSignupBinding
import likelion.project.ipet_seller.model.Seller
import likelion.project.ipet_seller.ui.main.MainActivity

class SignupFragment : Fragment() {

    lateinit var fragmentSignupBinding: FragmentSignupBinding
    lateinit var viewModel: SignupViewModel
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(this, SignupViewModelFactory(mainActivity))[SignupViewModel::class.java]
        fragmentSignupBinding = FragmentSignupBinding.inflate(inflater)
        initEvent()
        observe()
        return fragmentSignupBinding.root
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    "회원 가입 성공" -> {
                        mainActivity.removeFragment(MainActivity.SIGNUP_FRAGMENT)
                    }
                }
                Snackbar.make(fragmentSignupBinding.root, it, Snackbar.LENGTH_SHORT).show()
                fragmentSignupBinding.progressBarSignupLoading.visibility = View.GONE
            }
        }
    }

    private fun initEvent() {
        fragmentSignupBinding.materialButtonSignup.setOnClickListener {
            fragmentSignupBinding.progressBarSignupLoading.visibility = View.VISIBLE
            val result = getInputSellerInfo()
            if (result.isSuccess) {
                viewModel.onSignupButtonClickEvent(result.getOrNull()!!)
            } else {
                fragmentSignupBinding.progressBarSignupLoading.visibility = View.GONE
                showSnackbar(result.exceptionOrNull()?.message.toString())
            }
        }
    }

    private fun getInputSellerInfo(): Result<Seller> {
        return kotlin.runCatching {
            val seller = extractSellerFromInputs()
            validateSeller(seller)
            seller
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(fragmentSignupBinding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun extractSellerFromInputs(): Seller {
        return fragmentSignupBinding.run {
            Seller(
                sellerId = textInputEditTextSignupId.text.toString(),
                sellerPw = textInputEditTextSignupPasswd.text.toString(),
                sellerName = textInputEditTextSignupSellerName.text.toString(),
                sellerNumber = textInputEditTextSignupSellerNumber.text.toString().toIntOrNull()
                    ?: throw Exception("빈칸이 있습니다"),
                sellerPhone = textInputEditTextSignupSellerPhone.text.toString().toIntOrNull()
                    ?: throw Exception("빈칸이 있습니다"),
            )
        }
    }

    private fun validateSeller(seller: Seller): Boolean {
        return when {
            !isValidateId(seller.sellerId) -> throw Exception("아이디를 소문자, 숫자를 조합하여 최소 3글자, 최대 15글자로 입력해주세요")
            !isValidatePassword(seller.sellerPw) -> throw Exception("비밀번호를 소문자, 대문자, 숫자, 특수문자 조합하여 최소 8글자, 최대 20글자로 입력해주세요")
            !isCorrectPassword(
                seller.sellerPw,
                fragmentSignupBinding.textInputEditTextSignupPasswordConfirm.text.toString()
            ) -> throw Exception("비밀번호가 일치하지 않습니다")

            !isEditTextEmpty() -> throw Exception("빈칸이 있습니다")
            else -> true
        }
    }

    private fun isValidateId(sellerId: String): Boolean {
        val idPattern = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{3,15}\$"
        return sellerId.matches(idPattern.toRegex())
    }

    private fun isValidatePassword(sellerPw: String): Boolean {
        val pwPattern =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&#])[A-Za-z\\d@\$!%*?&#]{8,20}\$"
        return sellerPw.matches(pwPattern.toRegex())
    }

    private fun isCorrectPassword(sellerPw: String, sellerPwConfirm: String): Boolean {
        return sellerPw == sellerPwConfirm
    }

    private fun isEditTextEmpty(): Boolean {
        return fragmentSignupBinding.run {
            textInputEditTextSignupId.text.toString().isNotEmpty() &&
                    textInputEditTextSignupPasswd.text.toString().isNotEmpty() &&
                    textInputEditTextSignupPasswordConfirm.text.toString().isNotEmpty() &&
                    textInputEditTextSignupSellerName.text.toString().isNotEmpty() &&
                    textInputEditTextSignupSellerNumber.text.toString().isNotEmpty() &&
                    textInputEditTextSignupSellerPhone.text.toString().isNotEmpty()
        }
    }

}