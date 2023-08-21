package likelion.project.ipet_customer.ui.login

import android.content.Intent
import android.os.Bundle
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
import com.navercorp.nid.oauth.OAuthLoginCallback
import likelion.project.ipet_customer.databinding.FragmentLoginBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var mainActivity: MainActivity

    private val KAKAO_LOGIN = 0
    private val NAVER_LOGIN = 1
    private val GOOGLE_LOGIN = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentLoginBinding.run{
            buttonLoginKakao.run{
                setOnClickListener {
                    parsingLoginInfo(KAKAO_LOGIN)
                }
            }

            buttonLoginNaver.run{
                setOnClickListener {
                    parsingLoginInfo(NAVER_LOGIN)
                }
            }

            buttonLoginGoogle.run{
                setOnClickListener {
                    parsingLoginInfo(GOOGLE_LOGIN)
                }
            }
        }
        return fragmentLoginBinding.root
    }
    private fun parsingLoginInfo(loginCode: Int){
        when(loginCode){
            KAKAO_LOGIN -> {
                // 카카오 로그인
                UserApiClient.instance.loginWithKakaoTalk(mainActivity) { token, error ->
                    if (error != null) {
                        Log.e("login", "카카오 로그인 실패", error)
                    }
                    else if (token != null) {
                        Log.i("login", "카카오 로그인 성공 ${token.idToken}")
                    }
                }
            }
            NAVER_LOGIN -> {
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
            GOOGLE_LOGIN -> {
                // 구글 로그인
                val gso = GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
                val mGoogleSignInClient = GoogleSignIn.getClient(mainActivity, gso)
                val signInIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, GOOGLE_LOGIN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            GOOGLE_LOGIN -> {
                handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try{
            val account = completedTask.getResult(ApiException::class.java)
            Log.i("login", "구글 로그인 성공 ${account.id}")
        } catch (e: ApiException){
            Log.w("login", "구글 로그인 실패")
        }
    }
}