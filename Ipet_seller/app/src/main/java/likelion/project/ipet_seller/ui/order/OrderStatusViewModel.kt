package likelion.project.ipet_seller.ui.order

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.model.Order
import likelion.project.ipet_seller.model.Product
import likelion.project.ipet_seller.repository.OrderRepository
import likelion.project.ipet_seller.repository.ProductRepository
import likelion.project.ipet_seller.repository.SellerRepository

class OrderStatusViewModel(context: Context) : ViewModel() {
    private val sellerRepository = SellerRepository(context)
    private val orderRepository = OrderRepository()
    private val productRepository = ProductRepository()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val event = _event.asSharedFlow()

    fun fetchOrdersWithMatchingOrderNumber() {
        viewModelScope.launch {
            orderRepository.getOrdersWithMatchingOrderNumber(sellerRepository.readSellerIdToLocal().first)
                .collect {
                    it.onSuccess { orders ->
                        _uiState.update {
                            it.copy(
                                initOrderList = true,
                                orderList = orders
                            )
                        }
                    }
                }
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            productRepository.getProducts(sellerRepository.readSellerIdToLocal().first)
                .collect {
                    it.onSuccess { products ->
                        _uiState.update {
                            it.copy(
                                productList = products
                            )
                        }
                    }
                }
        }
    }

    fun onOrderButtonClickEvent(orders: List<Order>) {
        viewModelScope.launch {
            orderRepository.updateOrderStatus(orders, OrderStatus.PREPARING)
                .collect {
                    it.onSuccess {
                        _event.tryEmit("배송 준비 중으로 변경되었습니다")
                    }.onFailure {
                        _event.tryEmit(it.message.toString())
                        return@collect
                    }
                }
        }
    }
}

enum class OrderStatus(val number: Int) {
    BEFORE_PROCESSING(-1), PREPARING(0), DELIVERING(1), DELIVERED(2);

    companion object {
        fun of(number: Int): OrderStatus {
            return values().find { it.number == number } ?: throw IllegalArgumentException()
        }
    }
}

data class UiState(
    val initOrderList: Boolean = false,
    val orderList: Map<Long, List<Order>> = emptyMap(),
    val productList: List<Product> = emptyList(),
)