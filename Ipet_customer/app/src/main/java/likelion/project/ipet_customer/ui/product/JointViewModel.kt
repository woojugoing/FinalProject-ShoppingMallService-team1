import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import likelion.project.ipet_customer.model.Joint
import likelion.project.ipet_customer.repository.JointRepository

class JointViewModel(private val repository: JointRepository) : ViewModel() {

    private val joinTdataList = mutableListOf<Joint>()

    fun loadJointData(callback: () -> Unit) {
        repository.getJointData { data ->
            joinTdataList.clear()
            joinTdataList.addAll(data)
            callback()
        }
    }

    fun getJointList(): List<Joint> {
        return joinTdataList
    }
}