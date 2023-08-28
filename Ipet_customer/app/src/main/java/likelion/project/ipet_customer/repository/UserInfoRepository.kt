package likelion.project.ipet_customer.repository

import likelion.project.ipet_customer.db.remote.CustomerDataSource
import likelion.project.ipet_customer.model.Customer

class UserInfoRepository {
    private val customerDataSource = CustomerDataSource()

    // 새로운 사용자 정보 저장
    fun setAddress(customer: Customer){
        customerDataSource.setUserAddress(customer)
    }
}