package likelion.project.ipet_customer.repository

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import likelion.project.ipet_customer.db.remote.CustomerDataSource
import likelion.project.ipet_customer.model.Customer
import likelion.project.ipet_customer.ui.login.LoginViewModel
import likelion.project.ipet_customer.ui.main.MainActivity

class UserInfoRepository() {
    private val customerDataSource = CustomerDataSource()

    // 새로운 사용자 정보 저장
    fun setAddress(customer: Customer){
        customerDataSource.setUserAddress(customer)
    }

    // 회원 탈퇴
    fun signOut(customer: Customer, mainActivity: MainActivity){
        // 데이터 베이스에서 유저 데이터 삭제
        customerDataSource.deleteUserData(customer)

        // 로그인 방법에 따라 회원 탈퇴 진행
        when(customer.customerType){
            LoginViewModel.LOGIN_KAKAO -> {
                // 연결 끊기
                UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        // 회원 탈퇴 실패
                    }
                    else {
                        // 회원 탈퇴 성공
                    }
                }
            }
            LoginViewModel.LOGIN_NAVER -> {
                NidOAuthLogin().callDeleteTokenApi(object : OAuthLoginCallback {
                    override fun onSuccess() {
                        //서버에서 토큰 삭제에 성공한 상태
                    }
                    override fun onFailure(httpStatus: Int, message: String) {
                        // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태
                    }
                    override fun onError(errorCode: Int, message: String) {
                        // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태
                        onFailure(errorCode, message)
                    }
                })
            }
            LoginViewModel.LOGIN_GOOGLE -> {
                val gso =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                        .build()
                val mGoogleSignInClient = GoogleSignIn.getClient(mainActivity, gso)
                mGoogleSignInClient.revokeAccess().addOnSuccessListener {
                    // 회원 탈퇴 성공
                }
            }
        }
    }
}