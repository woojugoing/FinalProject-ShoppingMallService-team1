package likelion.project.ipet_customer.ui.login

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 카카오 SDK 초기화
        KakaoSdk.init(this, "5df3b9c98bc3e67c567b6fc7de06e997")

        // 네이버 SDK 초기화
        NaverIdLoginSDK.initialize(this, "wPnmtnY0lMjyWy4tuKDq", "qOYOWmu47l", "IPet")
    }
}