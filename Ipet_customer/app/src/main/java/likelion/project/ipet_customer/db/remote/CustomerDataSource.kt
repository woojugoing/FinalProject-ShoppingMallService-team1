package likelion.project.ipet_customer.db.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import likelion.project.ipet_customer.model.Customer

class CustomerDataSource {
    private val db = Firebase.firestore

    // 유저 정보 데이터 베이스 저장
    fun putUserData(customerId: String, customerName: String){
        val customer = Customer(customerId, customerName)
        db.collection("Customer").add(customer)
            .addOnSuccessListener {
        }
            .addOnFailureListener {
        }
    }

    // 유저 정보 데이터 베이스 추출
    fun getUserData(customerId: String) : Task<QuerySnapshot>{
        return db.collection("Customer")
            .whereEqualTo("customerId", customerId)
            .get()
    }
}