package likelion.project.ipet_customer.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import likelion.project.ipet_customer.databinding.FragmentLoginBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: LoginViewModel

    private val LOGIN_GOOGLE = 0

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
                    viewModel.socialLoginKakao()
                }
            }
            buttonLoginNaver.run{
                setOnClickListener {
                    viewModel.socialLoginNaver()
                }
            }
            buttonLoginGoogle.run{
                setOnClickListener {
                    startActivityForResult(viewModel.socialLoginGoogle(), LOGIN_GOOGLE)
                }
            }
        }
        return fragmentLoginBinding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            LOGIN_GOOGLE -> {
                viewModel.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
            }
        }
    }
}