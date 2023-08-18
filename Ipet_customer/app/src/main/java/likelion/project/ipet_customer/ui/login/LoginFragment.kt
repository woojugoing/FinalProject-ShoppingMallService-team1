package likelion.project.ipet_customer.ui.login

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentLoginBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentLoginBinding.run{
            buttonLoginKakao.run{
                setOnClickListener {
                    // 카카오 로그인
                    UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                        if (error != null) {
                            Log.e("login", "카카오 로그인 실패", error)
                        }
                        else if (token != null) {
                            Log.i("login", "카카오 로그인 성공 ${token.accessToken}")
                        }
                    }
                }
            }

            buttonLoginNaver.run{
                setOnClickListener {
                    // 네이버 로그인
                    val oauthLoginCallback = object : OAuthLoginCallback {
                        override fun onSuccess() {
                            // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                            Log.i("login", "네이버 로그인 성공")
                        }
                        override fun onFailure(httpStatus: Int, message: String) {
                            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                            Toast.makeText(context,"errorCode:$errorCode, errorDesc:$errorDescription",Toast.LENGTH_SHORT).show()
                        }
                        override fun onError(errorCode: Int, message: String) {
                            onFailure(errorCode, message)
                        }
                    }
                    NaverIdLoginSDK.authenticate(mainActivity, oauthLoginCallback)
                }
            }
        }
        return fragmentLoginBinding.root
    }
}