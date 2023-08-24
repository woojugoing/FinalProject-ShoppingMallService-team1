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

    fun loadAllJoints() {
        viewModelScope.launch {
            val joints = jointRepository.getAllJoint()
            jointsLiveData.postValue(joints)
        }
    }
}