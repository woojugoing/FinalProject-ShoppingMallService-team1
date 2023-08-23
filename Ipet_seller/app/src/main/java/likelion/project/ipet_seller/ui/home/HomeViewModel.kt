package likelion.project.ipet_seller.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.model.Order
import likelion.project.ipet_seller.model.Seller
import likelion.project.ipet_seller.repository.OrderRepository
import likelion.project.ipet_seller.repository.SellerRepository


class HomeViewModel(context: Context) : ViewModel() {
    private val orderRepository = OrderRepository()
    private val sellerRepository = SellerRepository(context)

    private val _uiState = MutableStateFlow(UiState())
    val uistate = _uiState.asStateFlow()

    fun fetchSeller() {
        viewModelScope.launch {
            val id = sellerRepository.readSellerIdToLocal().first
            val pw = sellerRepository.readSellerIdToLocal().second
            sellerRepository.getSellerInfo(Seller(sellerId = id, sellerPw = pw)).collect {
                it.onSuccess { seller ->
                    _uiState.update {
                        it.copy(
                            seller = seller
                        )
                    }
                }
            }
        }
    }

    fun fetchOrders() {
        viewModelScope.launch {
            val sellerId = sellerRepository.readSellerIdToLocal().first
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
    val seller: Seller? = null,
    val orderList: List<Order>? = null,
    val orderCount: Int = 0,
    val preparingCount: Int = 0,
    val deliveringCount: Int = 0,
    val deliveredCount: Int = 0
)