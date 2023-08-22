package likelion.project.ipet_customer.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentPaymentAddressBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class PaymentAddressFragment : Fragment() {

    lateinit var fragmentPaymentAddressBinding: FragmentPaymentAddressBinding
    lateinit var mainActivity: MainActivity
    val siteAddress = "https://searchaddress-2c847.web.app"

    inner class BridgeInterface {
        @JavascriptInterface
        fun processDATA(data: String?) {

            val newBundle = Bundle()
            newBundle.putString("address", data)
            mainActivity.replaceFragment(MainActivity.PAYMENT_FRAGMENT, false, newBundle)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPaymentAddressBinding = FragmentPaymentAddressBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentPaymentAddressBinding.run {

            webViewPaymentAddress.run {
                settings.javaScriptEnabled = true
                addJavascriptInterface(BridgeInterface(), "Android")
                webViewClient = object: WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        // Android -> Javascript 함수 호출
                        loadUrl("javascript:sample2_execDaumPostcode();")
                    }
                }
                // 최초 웹뷰 로드
                // loadUrl("https://billcoreatech.blogspot.com/2022/06/blog-post.html")
                loadUrl(siteAddress)
            }
        }
        return fragmentPaymentAddressBinding.root
    }

}