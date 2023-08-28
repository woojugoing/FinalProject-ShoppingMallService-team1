package likelion.project.ipet_customer.ui.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import likelion.project.ipet_customer.model.Joint
import likelion.project.ipet_customer.model.Product
import likelion.project.ipet_customer.model.Review
import likelion.project.ipet_customer.repository.JointRepository
import likelion.project.ipet_customer.repository.ProductRepository
import likelion.project.ipet_customer.repository.ReviewRepository

class ProductInfoViewModel : ViewModel() {
    private val productRepository = ProductRepository()
    private val jointRepository = JointRepository()
    private val reviewRepository = ReviewRepository()

    val productLiveData = MutableLiveData<Product>()
    val jointLiveData = MutableLiveData<Joint>()
    val reviewLiveData = MutableLiveData<MutableList<Review>>()

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

    fun loadSelectReview(productIdx : String){
        viewModelScope.launch{
            val reviews = reviewRepository.selectReview(productIdx)
            reviewLiveData.postValue(reviews)
        }
    }
}