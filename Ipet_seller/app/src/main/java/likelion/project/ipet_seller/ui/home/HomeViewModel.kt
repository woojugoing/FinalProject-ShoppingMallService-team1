package likelion.project.ipet_seller.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.model.Order
import likelion.project.ipet_seller.repository.OrderRepository


class HomeViewModel : ViewModel() {
    private val orderRepository = OrderRepository()

    private val _uiState = MutableStateFlow(UiState())
    val uistate = _uiState.asStateFlow()

    fun fetchOrders(sellerId: String) {
        viewModelScope.launch {
            orderRepository.getOrders(sellerId).collect {
                it.onSuccess { orders ->
                    _uiState.update {
                        it.copy(
                            orderList = orders,
                            orderCount = orders.count { it.orderState == -1 },
                            preparingCount = orders.count { it.orderState == 0 },
                            deliveringCount = orders.count { it.orderState == 1 },
                            deliveredCount = orders.count { it.orderState == 2 }
                        )
                    }
                }
            }
        }
    }
}

data class UiState(
    val orderList: List<Order>? = null,
    val orderCount: Int = 0,
    val preparingCount: Int = 0,
    val deliveringCount: Int = 0,
    val deliveredCount: Int = 0
)