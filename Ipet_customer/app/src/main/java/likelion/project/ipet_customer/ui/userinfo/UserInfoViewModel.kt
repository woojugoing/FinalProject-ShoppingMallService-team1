package likelion.project.ipet_customer.ui.userinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import likelion.project.ipet_customer.model.Customer
import likelion.project.ipet_customer.repository.UserInfoRepository
import likelion.project.ipet_customer.ui.login.LoginViewModel
import likelion.project.ipet_customer.ui.main.MainActivity

class UserInfoViewModel : ViewModel() {
    private val userInfoRepository = UserInfoRepository()
    fun saveAddress(customer: Customer){
        userInfoRepository.setAddress(customer)
    }

    fun logout(){
        LoginViewModel.customer = Customer()
    }

    fun signOut(customer: Customer, mainActivity: MainActivity){
        userInfoRepository.signOut(customer, mainActivity)
    }
}