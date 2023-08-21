package likelion.project.ipet_seller.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.databinding.FragmentLoginBinding
import likelion.project.ipet_seller.model.Seller
import likelion.project.ipet_seller.ui.main.MainActivity


class LoginFragment : Fragment() {

    lateinit var fragmentLoginBinding: FragmentLoginBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        initEvent()
        observe()
        return fragmentLoginBinding.root
    }

    private fun getSeller(): Seller {
        var seller = Seller()

        fragmentLoginBinding.run {
            seller = seller.copy(
                sellerId = textInputEditTextLoginId.text.toString(),
                sellerPw = textInputEditTextLoginPasswd.text.toString(),
            )
        }
        return seller
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.event.collect {
                when (it) {
                    "로그인 성공" -> {
                        navigateToHome()
                    }
                }
                Snackbar.make(fragmentLoginBinding.root, it, Snackbar.LENGTH_SHORT).show()
                fragmentLoginBinding.progressBarLoginLoading.visibility = View.GONE
            }
        }
    }

    private fun initEvent() {
        onLoginButtonClick()
        onSignupButtonClick()
    }

    private fun onLoginButtonClick() {
        fragmentLoginBinding.run {
            materialButtonLogin.run {
                setOnClickListener {
                    fragmentLoginBinding.progressBarLoginLoading.visibility = View.VISIBLE
                    viewModel.onLoginButtonEvent(getSeller())
                }
            }
        }
    }

    private fun onSignupButtonClick() {
        fragmentLoginBinding.run {
            materialButtonLoginSignup.run {
                setOnClickListener {
                    navigateToSignUp()
                }
            }
        }
    }

    private fun navigateToHome() {
        mainActivity.replaceFragment(MainActivity.HOME_FRAGMENT, false, null)
    }

    private fun navigateToSignUp() {
        mainActivity.replaceFragment(MainActivity.SIGNUP_FRAGMENT, true, null)
    }
}