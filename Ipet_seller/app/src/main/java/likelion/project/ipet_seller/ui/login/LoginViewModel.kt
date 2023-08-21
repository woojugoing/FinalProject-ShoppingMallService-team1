package likelion.project.ipet_seller.ui.login

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.model.Seller
import likelion.project.ipet_seller.repository.SellerRepository

class LoginViewModel : ViewModel() {
    private val sellerRepository = SellerRepository()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)
    val event = _event.asSharedFlow()

    suspend fun getSellerInfo(seller: Seller) {
        viewModelScope.launch {
            sellerRepository.getSellerInfo(seller)
                .collect {
                    it.onSuccess { sellerInfo ->
                        _uiState.update {
                            it.copy(
                                seller = sellerInfo,
                                isLogin = true,
                                throwableMessage = null,
                            )
                        }
                    }.onFailure { throwable ->
                        _uiState.update {
                            it.copy(
                                throwableMessage = throwable.message
                            )
                        }
                        delay(50)
                        _uiState.update {
                            it.copy(
                                throwableMessage = null
                            )
                        }
                    }
                }
        }
    }

    fun onLoginButtonEvent(seller: Seller) {
        viewModelScope.launch {
            getSellerInfo(seller)
            uiState.collectLatest {
                if (it.isLogin) {
                    _event.tryEmit("로그인 성공")
                } else if (it.throwableMessage != null){
                    _event.tryEmit("로그인 실패")
                }
            }
        }
    }
}

data class UiState(
    val seller: Seller? = null,
    val isLogin: Boolean = false,
    val throwableMessage: String? = null,
)