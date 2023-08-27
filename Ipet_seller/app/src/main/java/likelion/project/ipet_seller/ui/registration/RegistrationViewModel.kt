package likelion.project.ipet_seller.ui.registration

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.model.Product
import likelion.project.ipet_seller.repository.ProductRepository
import likelion.project.ipet_seller.repository.SellerRepository

class RegistrationViewModel(context: Context): ViewModel() {
    private val productRepository = ProductRepository()
    private val sellerRepository = SellerRepository(context)

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Boolean> = MutableSharedFlow(extraBufferCapacity = 1)
    val event = _event.asSharedFlow()

    fun registerProduct(product: Product) {
        viewModelScope.launch {
            productRepository.registerProduct(product).collect {
                it.onSuccess {
                    _uiState.update {
                        it.copy(
                            isSave = true
                        )
                    }
                }
            }
        }
    }

    fun onUploadClickEvent(product: Product) {
        viewModelScope.launch {
            registerProduct(product.copy(productSeller = sellerRepository.readSellerIdToLocal().first))
            uiState.collect {
                if (it.isSave) {
                    _event.tryEmit(true)
                } else {
                    _event.tryEmit(false)
                }
                delay(1000)
            }
        }
    }
}

data class UiState(
    val isSave: Boolean = false
)