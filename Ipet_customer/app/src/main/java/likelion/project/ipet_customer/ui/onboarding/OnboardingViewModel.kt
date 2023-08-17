package likelion.project.ipet_customer.ui.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import likelion.project.ipet_customer.repository.OnboardingRepository

class OnboardingViewModel(context: Context): ViewModel() {
    private val onboardingRepository = OnboardingRepository(context)

    fun writeFirstVisitor() {
        viewModelScope.launch {
            onboardingRepository.writeOnboarding(value = "IS_SHOWN")
        }
    }
}