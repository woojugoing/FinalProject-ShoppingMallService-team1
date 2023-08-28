package likelion.project.ipet_customer.ui.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import likelion.project.ipet_customer.model.Joint
import likelion.project.ipet_customer.model.Product
import likelion.project.ipet_customer.repository.JointRepository
import likelion.project.ipet_customer.repository.ProductRepository

class ProductInfoViewModel : ViewModel() {
    private val productRepository = ProductRepository()
    private val jointRepository = JointRepository()

    val productLiveData = MutableLiveData<Product>()
    val jointLiveData = MutableLiveData<Joint>()

    fun loadOneProduct(productIdx : String){
        viewModelScope.launch{
            val product = productRepository.getOneProduct(productIdx)
            productLiveData.postValue(product)
        }
    }

    fun loadOneJoint(jointIdx : Long){
        viewModelScope.launch{
            val joint = jointRepository.getOneJoint(jointIdx)
            jointLiveData.postValue(joint)
        }
    }
}