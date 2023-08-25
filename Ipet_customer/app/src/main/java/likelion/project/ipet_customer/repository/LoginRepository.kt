package likelion.project.ipet_customer.repository

import likelion.project.ipet_customer.db.remote.CustomerDataSource
import likelion.project.ipet_customer.model.Customer
import likelion.project.ipet_customer.ui.login.LoginViewModel

class LoginRepository {
    private val customerDataSource = CustomerDataSource()

    fun login(customer: Customer){
        customerDataSource.getUserData(customer.customerId).addOnSuccessListener {
            if(it.isEmpty){
                // 기존 아이디 없음
                customerDataSource.putUserData(customer.customerId, customer.customerName)
                LoginViewModel.customer = customer
            }else{
                // 기존 아이디 존재
                LoginViewModel.customer = Customer(it.documents[0]["customerId"].toString()
                    ,it.documents[0]["customerName"].toString())
            }
        }
    }
}