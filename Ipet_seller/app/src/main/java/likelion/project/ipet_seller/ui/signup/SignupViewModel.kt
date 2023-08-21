package likelion.project.ipet_seller.ui.signup

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

class SignupViewModel : ViewModel() {
    private val sellerRepository = SellerRepository()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val event = _event.asSharedFlow()

    suspend fun signup(seller: Seller) {
        viewModelScope.launch {
            sellerRepository.signupSeller(seller).collect {
                it.onSuccess { isSignup ->
                    _uiState.update {
                        it.copy(
                            isSignup = true
                        )
                    }
                }.onFailure { throwable ->
                    _uiState.update {
                        it.copy(throwableMessage = throwable.message)
                    }
                }
            }

        }
    }

    fun onSignupButtonClickEvent(seller: Seller) {
        viewModelScope.launch {
            signup(seller)
            delay(500)
            uiState.collectLatest {
                if (it.isSignup) {
                    _event.tryEmit("회원 가입 성공")
                } else if (it.throwableMessage != null){
                    _event.tryEmit(it.throwableMessage.toString())
                }
            }
        }
    }
}

data class UiState(
    val isSignup: Boolean = false,
    val throwableMessage: String? = null
)