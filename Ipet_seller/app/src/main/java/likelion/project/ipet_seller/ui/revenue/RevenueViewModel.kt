package likelion.project.ipet_seller.ui.revenue

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.model.Order
import likelion.project.ipet_seller.model.Product
import likelion.project.ipet_seller.model.Revenue
import likelion.project.ipet_seller.model.Seller
import likelion.project.ipet_seller.repository.OrderRepository
import likelion.project.ipet_seller.repository.ProductRepository
import likelion.project.ipet_seller.repository.SellerRepository

class RevenueViewModel(context: Context) : ViewModel() {
    private val orderRepository = OrderRepository()
    private val sellerRepository = SellerRepository(context)
    private val productRepository = ProductRepository()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun fetchOrders() {
        viewModelScope.launch {
            orderRepository.getOrders(sellerRepository.readSellerIdToLocal().first)
                .collect {
                    it.onSuccess { orders ->
                        _uiState.update {
                            it.copy(
                                initOrderList = true,
                                orderList = orders.sortedBy { it.orderDate },
                            )
                        }
                    }
                }
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            val sellerId = sellerRepository.readSellerIdToLocal().first
            productRepository.getProducts(sellerId).collect {
                it.onSuccess { products ->
                    _uiState.update {
                        it.copy(
                            initProduectList = true,
                            productList = products
                        )
                    }
                }
            }

        }
    }
}

data class UiState(
    val initOrderList: Boolean = false,
    val initProduectList: Boolean = false,
    val productList: List<Product> = emptyList(),
    val orderList: List<Order> = emptyList(),
    val revenueList: List<Revenue> = emptyList()
)