package likelion.project.ipet_customer.ui.login

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
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
                    // 카카오톡으로 로그인
                    UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                        if (error != null) {
                            Log.e("login", "로그인 실패", error)
                        }
                        else if (token != null) {
                            Log.i("login", "로그인 성공 ${token.accessToken}")
                        }
                    }
                }
            }
        }
        return fragmentLoginBinding.root
    }
}