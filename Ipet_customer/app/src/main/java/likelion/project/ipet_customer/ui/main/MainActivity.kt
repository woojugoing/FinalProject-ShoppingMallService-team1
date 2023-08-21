package likelion.project.ipet_customer.ui.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.ActivityMainBinding
import likelion.project.ipet_customer.ui.coupon.CouponFragment
import likelion.project.ipet_customer.ui.heart.HeartFragment
import likelion.project.ipet_customer.ui.home.HomeFragment
import likelion.project.ipet_customer.ui.order.OrderStatusFragment
import likelion.project.ipet_customer.ui.login.LoginFragment
import likelion.project.ipet_customer.ui.onboarding.OnboardFragment
import likelion.project.ipet_customer.ui.permission.PermissionFragment
import likelion.project.ipet_customer.ui.product.ProductInfoFragment
import likelion.project.ipet_customer.ui.product.ProductListFragment
import likelion.project.ipet_customer.ui.review.ReviewAllFragment
import likelion.project.ipet_customer.ui.review.ReviewWriteFragment
import likelion.project.ipet_customer.ui.shoppingbasket.ShoppingBasketFragment
import likelion.project.ipet_customer.ui.userinfo.UserInfoMainFragment


class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    var newFragment: Fragment? = null
    var oldFragment: Fragment? = null

    val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.CALL_PHONE,
    )

    private var isFirstVisitor = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, MainViewModelFactory(this))[MainViewModel::class.java]
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        observe()
        navigateToPermissionOrOnboardingOrLogin()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.isFistVisitor.collect {
                isFirstVisitor = it
            }
        }
    }

    private fun navigateToPermissionOrOnboardingOrLogin() {
        CoroutineScope(Dispatchers.Main).launch {
            if ((checkPermission() || shouldShowPermissionRationale()) && isFirstVisitor) {
                replaceFragment(ONBOARDING_FRAGMENT, false, null)
            } else if (!isFirstVisitor) {
                replaceFragment(LOGIN_FRAGMENT, false, null)
            } else {
                replaceFragment(PERMISSION_FRAGMENT, false, null)
            }
            delay(500)
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
            SHOPPING_BASKET_FRAGMENT -> ShoppingBasketFragment()
            REVIEWALL_FRAGMENT -> ReviewAllFragment()
            REVIEW_WRITE_FRAGMENT -> ReviewWriteFragment()
            PERMISSION_FRAGMENT -> PermissionFragment()
            ONBOARDING_FRAGMENT -> OnboardFragment()
            LOGIN_FRAGMENT -> LoginFragment()
            PRODUCT_LIST_FRAGMENT -> ProductListFragment()
            PRODUCT_INFO_FRAGMENT -> ProductInfoFragment()
            USER_INFO_MAIN_FRAGMENT -> UserInfoMainFragment()
            COUPON_FRAGMENT -> CouponFragment()
            HEART_FRAGMENT -> HeartFragment()
            ORDER_STATUS_FRAGMENT -> OrderStatusFragment()
            HOME_FRAGMENT -> HomeFragment()
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
        val SHOPPING_BASKET_FRAGMENT = "ShoppingBasketFragment"
        val REVIEWALL_FRAGMENT = "ReviewAllFragment"
        val REVIEW_WRITE_FRAGMENT = "ReviewWriteFragment"
        val PERMISSION_FRAGMENT = "PermissionFragment"
        val ONBOARDING_FRAGMENT = "OnboardingFragment"
        val LOGIN_FRAGMENT = "LoginFragment"
        val PRODUCT_LIST_FRAGMENT = "ProductListFragment"
        val PRODUCT_INFO_FRAGMENT = "ProductInfoFragment"
        val USER_INFO_MAIN_FRAGMENT = "UserInfoMainFragment"
        val COUPON_FRAGMENT = "CouponFragment"
        val HEART_FRAGMENT = "HeartFragment"
        val ORDER_STATUS_FRAGMENT = "OrderStatusFragment"
        val HOME_FRAGMENT = "HomeFragment"
        const val PERMISSION_REQUEST_ACCESS = 100
    }
}