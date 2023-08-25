package likelion.project.ipet_customer.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import likelion.project.ipet_customer.model.Order

class OrderRepository {
    private val db = Firebase.firestore

    suspend fun getAllOrder(): MutableList<Order> {
        val querySnapshot = db.collection("Order").get().await()
        return querySnapshot.toObjects(Order::class.java)
    }
}