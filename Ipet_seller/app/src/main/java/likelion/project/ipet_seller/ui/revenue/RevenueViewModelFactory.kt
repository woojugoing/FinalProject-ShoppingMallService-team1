package likelion.project.ipet_seller.ui.revenue

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import likelion.project.ipet_seller.ui.signup.SignupViewModel

class RevenueViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RevenueViewModel(context) as T
    }
}