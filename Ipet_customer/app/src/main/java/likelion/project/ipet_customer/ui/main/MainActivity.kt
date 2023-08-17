package likelion.project.ipet_customer.ui.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.transition.MaterialSharedAxis
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.ActivityMainBinding
import likelion.project.ipet_customer.ui.onboarding.OnboardFragment
import likelion.project.ipet_customer.ui.permission.PermissionFragment
import likelion.project.ipet_customer.ui.review.ReviewAllFragment

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    var newFragment: Fragment? = null
    var oldFragment: Fragment? = null

    val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.CALL_PHONE,
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        startSplash()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        navigateToPermissionOrOnboarding()
    }

    private fun navigateToPermissionOrOnboarding() {
        when {
            checkPermission() || shouldShowPermissionRationale() -> replaceFragment(ONBOARDING_FRAGMENT, false, null)
            else -> replaceFragment(PERMISSION_FRAGMENT, false, null)
        }
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
            REVIEWALL_FRAGMENT -> ReviewAllFragment()
            PERMISSION_FRAGMENT -> PermissionFragment()
            ONBOARDING_FRAGMENT -> OnboardFragment()
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

    fun shouldShowPermissionRationale(): Boolean {
        return permissionList.any { permission ->
            shouldShowRequestPermissionRationale(permission)
        }
    }

    fun checkPermission(): Boolean {
        return permissionList.all { permission ->
            ActivityCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun removeFragment(name: String) {
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {
        val REVIEWALL_FRAGMENT = "ReviewAllFragment"
        val PERMISSION_FRAGMENT = "PermissionFragment"
        val ONBOARDING_FRAGMENT = "OnboardingFragment"
        const val PERMISSION_REQUEST_ACCESS = 100
    }
}