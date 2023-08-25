package likelion.project.ipet_customer.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import likelion.project.ipet_customer.model.Joint
import likelion.project.ipet_customer.repository.JointRepository

class HomeViewModel : ViewModel() {
    private val jointRepository = JointRepository()

    val jointsLiveData = MutableLiveData<MutableList<Joint>>()

    // 현재 필터링 조건을 나타내는 변수
    private val currentFilter = MutableLiveData<String>()

    init {
        // 초기 필터링 조건 설정
        currentFilter.value = "강아지"
    }

    fun loadFilteredJoints() {
        viewModelScope.launch {
            val joints = jointRepository.getAllJoint()
            val filteredJoints = filterJoints(joints, currentFilter.value)

            jointsLiveData.postValue(filteredJoints)
        }
    }

    // 필터링된 데이터를 반환하는 함수
    private fun filterJoints(joints: List<Joint>, filter: String?): MutableList<Joint> {
        val filteredList = mutableListOf<Joint>()

        filter?.let {
            for (joint in joints) {
                if (joint.jointAnimalType == it) {
                    filteredList.add(joint)
                }
            }
        }

        return filteredList
    }

    // 필터링 조건 변경 시 호출되는 함수
    fun updateFilter(newFilter: String) {
        currentFilter.value = newFilter
        loadFilteredJoints()
    }
}