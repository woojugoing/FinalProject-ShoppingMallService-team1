package likelion.project.ipet_customer.ui.product

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import likelion.project.ipet_customer.model.Product
import likelion.project.ipet_customer.repository.ProductRepository

class ProductInfoViewModel : ViewModel() {
    private val productRepository = ProductRepository()

    val productLiveData = MutableLiveData<Product>()

    fun loadOneProduct(productIdx : Long){
        viewModelScope.launch{
            val product = productRepository.getOneProduct(productIdx)
            productLiveData.postValue(product)
        }
    }
}