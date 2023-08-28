package likelion.project.ipet_customer.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
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
    private var loginFragment = LoginFragment()
    private var loginRepository = LoginRepository()

    // 로그인
    fun login(view: View, socialCode: Int){
        when(socialCode){
            LOGIN_KAKAO -> {
                loginKakao(view)
            }
            LOGIN_NAVER -> {
                loginNaver()
            }
        }
    }

    // 카카오 로그인 메서드
    fun loginKakao(view: View) {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                // 카카오톡이 설치되어있지 않은 경우 SnackBar로 표시
                loginFragment.showSnackBar(view, LOGIN_KAKAO_UNLOGIN)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                val idTokenList = token.idToken?.split(".")
                if (idTokenList != null) {
                    // 사용자 규격 정보
                    val idTokenPayload =
                        Base64.decode(idTokenList[1], Base64.DEFAULT).toString(Charsets.UTF_8)
                    val payloadJSONObject = JSONObject(idTokenPayload)
                    val customerNickname = payloadJSONObject["nickname"].toString()
                    val customerId = payloadJSONObject["sub"].toString()
                    val customer = Customer(customerId, customerNickname)
                    // 로그인
                    loginWithDatabase(customer)
                }
            }
        }

        // 카카오 실행 가능 여부 검사
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(mainActivity)) {
            // 카카오 로그인
            UserApiClient.instance.loginWithKakaoTalk(mainActivity) { token, error ->
                if (error != null) {
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(
                        mainActivity,
                        callback = callback
                    )
                }
                // 카카오 로그인 성공
                else if (token != null) {
                    val idTokenList = token.idToken?.split(".")
                    if (idTokenList != null) {
                        // 사용자 규격 정보
                        val idTokenPayload = Base64.decode(idTokenList[1], Base64.DEFAULT)
                            .toString(Charsets.UTF_8)
                        Log.i("login", idTokenPayload)
                        val payloadJSONObject = JSONObject(idTokenPayload)
                        val customerNickname = payloadJSONObject["nickname"].toString()
                        val customerId = payloadJSONObject["sub"].toString()
                        var customerEmail = try {
                            payloadJSONObject["email"].toString()
                        } catch (e: Exception){
                            "사욪자 제공 미동의"
                        }
                        val customer = Customer(customerId, customerNickname, customerEmail)
                        // 로그인
                        loginWithDatabase(customer)
                    }
                }
            }
        } else {
            // 카카오톡이 설치되어있지 않은 경우 SnackBar로 표시
            loginFragment.showSnackBar(view, LOGIN_KAKAO_UNINSTALL)
        }
    }

    // 네이버 로그인 메서드
    fun loginNaver() {
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증 성공
                val profileCallback = object : NidProfileCallback<NidProfileResponse> {
                    // 네이버 프로필 요청 성공
                    override fun onSuccess(response: NidProfileResponse) {
                        // 사용자 규격 정보
                        val customerNickname = response.profile?.name.toString()
                        val customerId = response.profile?.id.toString()
                        val customer = Customer(customerId, customerNickname)
                        // 로그인
                        loginWithDatabase(customer)
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                        val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    }

                    override fun onError(errorCode: Int, message: String) {
                        onFailure(errorCode, message)
                    }
                }

                // 프로필 정보 요청
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

        // 로그인 요청
        NaverIdLoginSDK.authenticate(mainActivity, oauthLoginCallback)
    }

    // 구글 로그인 메서드
    fun loginGoogle() : Intent{
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(mainActivity, gso)
        return mGoogleSignInClient.signInIntent
    }

    fun handleGoogleLoginResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            // 사용자 규격 정보
            val account = completedTask.getResult(ApiException::class.java)
            val customerNickname = account.displayName
            val customerId = account.id
            if (customerId != null && customerNickname != null) {
                val customer = Customer(customerId, customerNickname)
                // 로그인
                loginWithDatabase(customer)
            }
        } catch (e: ApiException) {
            Log.w("login", "구글 로그인 실패")
        }
    }

    // 사용자 중복 검사 후 데이터 삽입 및 추출
    fun loginWithDatabase(customer: Customer) {
        loginRepository.login(customer)
        mainActivity.replaceFragment(MainActivity.HOME_FRAGMENT, false, null)
    }

    companion object {
        // 기기 로그인 사용자 정보
        var customer = Customer()
        // 로그인 상태 구분
        val LOGIN_KAKAO = 0
        val LOGIN_NAVER = 1
        val LOGIN_GOOGLE = 2
        val LOGIN_KAKAO_UNINSTALL = 3
        val LOGIN_KAKAO_UNLOGIN = 4
    }
}