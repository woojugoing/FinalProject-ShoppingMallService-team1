package likelion.project.ipet_customer.ui.login

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 카카오 SDK 초기화
        KakaoSdk.init(this, "dbf9edd6dd9fbdefd7665d4e9cec9019")

        // 네이버 SDK 초기화
        NaverIdLoginSDK.initialize(this, "wPnmtnY0lMjyWy4tuKDq", "qOYOWmu47l", "IPet")
    }
}