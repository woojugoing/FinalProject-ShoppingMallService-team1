package likelion.project.ipet_customer.db.remote

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import likelion.project.ipet_customer.model.Customer

class CustomerDataSource {
    private val db = Firebase.firestore

    // 초기 유저의 정보를 Firestore에 저장
    fun signUp(customerId: String, customerName: String){
        val customer = Customer(customerId, customerName)
        db.collection("Customer").add(customer).addOnSuccessListener {
            Log.i("login", "$customerId 신규 로그인 성공")
        }.addOnFailureListener {
            Log.i("login", "$customerId 신규 로그인 실패")
        }
    }

    // 기존 유저의 정보를 Firestore에서 추출
    fun signIn(customerId: String){
        db.collection("Customer")
            .get()
            .addOnSuccessListener {
                Log.i("login", "기존 로그인 성공")
            }
            .addOnFailureListener {
                Log.i("login", "기존 로그인 실패")
            }
    }

    // 사용자의 정보 중복 검사
    fun searchCustomer(customerId: String) : Boolean{
        var result = false
        db.collection("Customer")
            .whereEqualTo("customerId", customerId)
            .get()
            .addOnSuccessListener {
                result = true
            }
            .addOnFailureListener {
                result = false
            }
        return result
    }
}