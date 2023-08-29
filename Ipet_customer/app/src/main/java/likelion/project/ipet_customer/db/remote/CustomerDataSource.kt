package likelion.project.ipet_customer.db.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import likelion.project.ipet_customer.model.Customer

class CustomerDataSource {
    private val db = Firebase.firestore

    // 유저 정보 데이터 베이스 저장
    fun putUserData(customer: Customer){
        db.collection("Customer").add(customer)
            .addOnSuccessListener {
        }
            .addOnFailureListener {
        }
    }

    // 유저 정보 데이터 베이스 추출
    fun getUserData(customer: Customer) : Task<QuerySnapshot>{
        val customerId = customer.customerId
        return db.collection("Customer")
            .whereEqualTo("customerId", customerId)
            .get()
    }

    // 유저 주소 데이터 베이스 변경
    fun setUserAddress(customer: Customer){
        getUserData(customer).addOnSuccessListener {
            val filePath = it.documents[0].reference.path
            db.document(filePath).update("customerAddressAddress", customer.customerAddressAddress)
            db.document(filePath).update("customerAddressDetail", customer.customerAddressDetail)
        }
    }
}