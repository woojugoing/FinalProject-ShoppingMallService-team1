package likelion.project.ipet_customer.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.ActivitySplashBinding
import likelion.project.ipet_customer.ui.main.MainActivity
import likelion.project.ipet_customer.ui.main.MainViewModel
import likelion.project.ipet_customer.ui.main.MainViewModelFactory

class SplashActivity : AppCompatActivity() {

    lateinit var activitySplashBinding: ActivitySplashBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        startSplash()
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, MainViewModelFactory(this))[MainViewModel::class.java]
        activitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(activitySplashBinding.root)
        showLogo()
        navigateToMain()
    }

    private fun navigateToMain() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showLogo() {
        activitySplashBinding.run {
            Glide.with(this@SplashActivity)
                .asGif()
                .load(R.drawable.logo_ipet)
                .into(imageViewSplashLogo)
        }
    }

    private fun startSplash() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                return@setKeepOnScreenCondition false
            }
        }
    }
}