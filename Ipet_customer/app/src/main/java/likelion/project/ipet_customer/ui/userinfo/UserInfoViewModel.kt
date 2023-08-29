package likelion.project.ipet_customer.ui.userinfo

import androidx.lifecycle.ViewModel
import likelion.project.ipet_customer.model.Customer
import likelion.project.ipet_customer.repository.UserInfoRepository
import likelion.project.ipet_customer.ui.login.LoginViewModel

class UserInfoViewModel : ViewModel() {
    private val userInfoRepository = UserInfoRepository()
    fun saveAddress(customer: Customer){
        userInfoRepository.setAddress(customer)
    }

    fun logout(){
        LoginViewModel.customer = Customer()
    }
}