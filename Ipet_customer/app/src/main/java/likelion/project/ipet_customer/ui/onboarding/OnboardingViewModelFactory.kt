package likelion.project.ipet_customer.ui.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import likelion.project.ipet_customer.ui.main.MainViewModel

class OnboardingViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OnboardingViewModel(context) as T
    }
}