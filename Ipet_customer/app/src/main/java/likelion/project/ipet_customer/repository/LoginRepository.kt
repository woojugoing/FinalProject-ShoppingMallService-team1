package likelion.project.ipet_customer.repository

import likelion.project.ipet_customer.db.remote.CustomerDataSource
import likelion.project.ipet_customer.model.Customer

class LoginRepository {
    private val customerDataSource = CustomerDataSource()

    // 초기 유저의 정보를 Firestore에 저장
    fun signUp(customer: Customer){
        customerDataSource.signUp(customer.customerId, customer.customerName)
    }

    // 기존 유저의 정보를 Firestore에서 추출
    fun signIn(customer: Customer){
        customerDataSource.signIn(customer.customerId)
    }

    // 사용자 정보 중복 검사
    fun searchCustomer(customer: Customer) : Boolean{
        return customerDataSource.searchCustomer(customer.customerId)
    }
}