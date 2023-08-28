package likelion.project.ipet_customer.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import likelion.project.ipet_customer.model.Joint
import likelion.project.ipet_customer.model.Product
import likelion.project.ipet_customer.repository.ProductRepository
import likelion.project.ipet_customer.repository.JointRepository
import likelion.project.ipet_customer.repository.OrderRepository

class HomeViewModel : ViewModel() {
    private val jointRepository = JointRepository()
    private val productRepository = ProductRepository()
    private val orderRepository = OrderRepository()

    val jointsLiveData = MutableLiveData<MutableList<Joint>>()
    val productLiveData = MutableLiveData<MutableList<Product>>()

    // 현재 필터링 조건을 나타내는 변수
    private val currentFilter = MutableLiveData<String>()

    init {
        // 초기 필터링 조건 설정
        currentFilter.value = "강아지"
    }

    fun loadFilteredJoints() {
        viewModelScope.launch {
            val joints = jointRepository.getAllJoint()
            val filteredJoints = filterAnimalType(joints) { joint ->
                joint.jointAnimalType == currentFilter.value
            }

            jointsLiveData.postValue(filteredJoints)
        }
    }

    // 필터링된 데이터를 반환하는 함수
    private fun <T> filterAnimalType(list: List<T>, filterPredicate: (T) -> Boolean): MutableList<T> {
        val filteredList = mutableListOf<T>()

        for (item in list) {
            if (filterPredicate(item)) {
                filteredList.add(item)
            }
        }

        return filteredList
    }

    // 필터링 조건 변경 시 호출되는 함수
    fun updateFilter(newFilter: String) {
        currentFilter.value = newFilter
        loadFilteredJoints()
        loadFilteredOrder()
    }

    fun loadFilteredOrder(){
        viewModelScope.launch {
            val orders = orderRepository.getAllOrder()
            val products = productRepository.getAllProduct()
            val groupedByProductIdx = orders.groupBy { it.productIdx }

            val sortedGroupedOrders = groupedByProductIdx
                .toList()
                .sortedByDescending { (_, orders) -> orders.size }
                .toMap()

            val mappingList = sortedGroupedOrders
                .flatMap { (productIdx, _) ->
                    products.filter { it.productIdx.equals(productIdx) }
                }

            val filteredProducts = filterAnimalType(mappingList) { product ->
                product.productAnimalType == currentFilter.value
            }

            productLiveData.postValue(filteredProducts)
        }
    }
}