package likelion.project.ipet_customer.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.ActivityMainBinding
import likelion.project.ipet_customer.viewmodel.MainViewModel
import java.time.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    var newFragment: Fragment? = null
    var oldFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        startSplash()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        replaceFragment(PERMISSION_FRAGMENT, false, null)
    }

    private fun startSplash() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                return@setKeepOnScreenCondition viewModel.isLoading.value
            }
        }
    }

    fun replaceFragment(name: String, addToBackStack: Boolean, bundle: Bundle?) {
        SystemClock.sleep(200)
        setOldFragmentTransition()
        newFragment = getFragmentByName(name)
        newFragment?.arguments = bundle
        setNewFragmentTransition()
        performFragmentTransaction(addToBackStack, name)
    }

    private fun getFragmentByName(name: String): Fragment? {
        return when (name) {
            PERMISSION_FRAGMENT -> PermissionFragment()
            else -> Fragment()
        }
    }

    private fun setOldFragmentTransition() {
        if (newFragment != null) {
            oldFragment = newFragment
            oldFragment?.apply {
                exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
                enterTransition = null
                returnTransition = null
            }
        }
    }

    private fun setNewFragmentTransition() {
        newFragment?.apply {
            exitTransition = null
            reenterTransition = null
            enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
            returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        }
    }

    private fun performFragmentTransaction(addToBackStack: Boolean, name: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        newFragment?.let { fragment ->
            fragmentTransaction.replace(R.id.fragmentContainerView_main_container, fragment)
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(name)
            }
            fragmentTransaction.commit()
        }
    }

    fun removeFragment(name: String) {
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {
        val PERMISSION_FRAGMENT = "PermissionFragment"
    }
}