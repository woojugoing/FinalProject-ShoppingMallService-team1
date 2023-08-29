package likelion.project.ipet_customer.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import likelion.project.ipet_customer.model.Cart

class CartRepository {
    private val db = Firebase.firestore

    suspend fun setAddCart(cart : Cart){
        withContext(Dispatchers.IO) {
            db.collection("Cart")
                .add(cart)
                .await()
        }
    }
}