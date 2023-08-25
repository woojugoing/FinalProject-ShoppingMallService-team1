package likelion.project.ipet_customer.ui.login

import android.content.Intent
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
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
import likelion.project.ipet_customer.model.Customer
import likelion.project.ipet_customer.repository.LoginRepository
import likelion.project.ipet_customer.ui.main.MainActivity
import org.json.JSONObject

class LoginViewModel(mainActivity: MainActivity) : ViewModel() {

    private var mainActivity = mainActivity
    private var loginRepository = LoginRepository()

    // 카카오 로그인 메서드
    fun socialLoginKakao(){
        UserApiClient.instance.loginWithKakaoTalk(mainActivity) { token, error ->
            if (error != null) {
                Log.e("login", "카카오 로그인 실패", error)
            }
            else if (token != null) {
                // 사용자 정보 추출
                val idTokenList = token.idToken?.split(".")
                if(idTokenList != null){
                    // 사용자 규격 정보
                    val idTokenPayload = Base64.decode(idTokenList[1], Base64.DEFAULT).toString(Charsets.UTF_8)
                    val payloadJSONObject = JSONObject(idTokenPayload)
                    val customerNickname = payloadJSONObject["nickname"].toString()
                    val customerId = payloadJSONObject["sub"].toString()
                    val customer = Customer(customerId, customerNickname)

                    // 로그인
                    login(customer)
                }
            }
        }
    }

    // 네이버 로그인 메서드
    fun socialLoginNaver(){
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증 성공
                val profileCallback = object : NidProfileCallback<NidProfileResponse> {
                    override fun onSuccess(response: NidProfileResponse) {
                        Log.i("login", "네이버 로그인 성공 ${response.profile?.name.toString()}, ${response.profile?.id.toString()}")
                    }
                    override fun onFailure(httpStatus: Int, message: String) {
                        val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                        val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    }
                    override fun onError(errorCode: Int, message: String) {
                        onFailure(errorCode, message)
                    }
                }
                NidOAuthLogin().callProfileApi(profileCallback)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        NaverIdLoginSDK.authenticate(mainActivity, oauthLoginCallback)
    }

    // 구글 로그인 메서드
    fun socialLoginGoogle(): Intent {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val mGoogleSignInClient = GoogleSignIn.getClient(mainActivity, gso)

        return mGoogleSignInClient.signInIntent
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try{
            val account = completedTask.getResult(ApiException::class.java)
            Log.i("login", "구글 로그인 성공 ${account.displayName}, ${account.id}")
        } catch (e: ApiException){
            Log.w("login", "구글 로그인 실패")
        }
    }

    // 사용자 중복 검사 후 데이터 삽입 및 추출
    fun login(customer: Customer){
        loginRepository.login(customer)
        mainActivity.replaceFragment(MainActivity.HOME_FRAGMENT, false, null)
    }

    companion object{
        var customerInfo = Customer()

        val LOGIN_KAKAO = 0
        val LOGIN_NAVER = 1
        val LOGIN_GOOGLE = 2
    }
}