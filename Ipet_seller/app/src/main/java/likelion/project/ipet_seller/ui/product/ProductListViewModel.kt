package likelion.project.ipet_seller.ui.product

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.model.Product
import likelion.project.ipet_seller.repository.ProductRepository
import likelion.project.ipet_seller.repository.SellerRepository

class ProductListViewModel(context: Context) : ViewModel() {
    private val sellerRepository = SellerRepository(context)
    private val productRepository = ProductRepository()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Boolean> = MutableSharedFlow(extraBufferCapacity = 1)
    val event = _event.asSharedFlow()

    fun fetchProducts() {
        viewModelScope.launch {
            val sellerId = sellerRepository.readSellerIdToLocal().first
            productRepository.getProducts(sellerId).collect {
                it.onSuccess { products ->
                    _uiState.update {
                        it.copy(
                            initProductList = true,
                            productList = products
                        )
                    }
                }
            }

        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productRepository.deleteProduct(product).collect {
                it.onSuccess {
                    _event.tryEmit(true)

                }.onFailure {
                    _event.tryEmit(false)
                }
            }
        }
    }
}

data class UiState(
    val initProductList: Boolean = false,
    val productList: List<Product> = emptyList(),
)