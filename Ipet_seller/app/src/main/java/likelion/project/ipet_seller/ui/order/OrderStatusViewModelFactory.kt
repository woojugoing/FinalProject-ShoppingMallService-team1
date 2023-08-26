package likelion.project.ipet_seller.ui.order

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OrderStatusViewModelFactory(val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderStatusViewModel(context) as T
    }
}