package likelion.project.ipet_customer.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.snackbar.Snackbar
import likelion.project.ipet_customer.databinding.FragmentLoginBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        viewModel = LoginViewModel(mainActivity)

        fragmentLoginBinding.run{
            buttonLoginKakao.run{
                setOnClickListener {
                    viewModel.login(it, LoginViewModel.LOGIN_KAKAO)
                }
            }
            buttonLoginNaver.run{
                setOnClickListener {
                    viewModel.login(it, LoginViewModel.LOGIN_NAVER)
                }
            }
            buttonLoginGoogle.run{
                setOnClickListener {
                    startActivityForResult(viewModel.loginGoogle(), LoginViewModel.LOGIN_GOOGLE)
                }
            }
        }
        return fragmentLoginBinding.root
    }

    fun showSnackBar(view: View, status: Int){
        when(status){
            LoginViewModel.LOGIN_KAKAO_UNINSTALL -> {
                Snackbar.make(view,"카카오톡이 설치되지 않았습니디", Snackbar.LENGTH_LONG)
                    .setAction("확인"){

                    }
                    .show()
            }
            LoginViewModel.LOGIN_KAKAO_UNLOGIN -> {
                Snackbar.make(view,"카카오톡에 먼저 로그인 해주세요", Snackbar.LENGTH_LONG)
                    .setAction("확인"){

                    }
                    .show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            LoginViewModel.LOGIN_GOOGLE -> {
                viewModel.handleGoogleLoginResult(GoogleSignIn.getSignedInAccountFromIntent(data))
            }
        }
    }
}