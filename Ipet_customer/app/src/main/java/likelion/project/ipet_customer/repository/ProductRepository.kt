package likelion.project.ipet_customer.repository

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import likelion.project.ipet_customer.model.Product

class ProductRepository {
    private val db = Firebase.firestore

    suspend fun getAllProduct() : MutableList<Product>{
        val querySnapshot = db.collection("Product").get().await()
        return querySnapshot.toObjects(Product::class.java)
    }

    suspend fun getOneProduct(productIdx: String): Product {
        val query = db.collection("Product").whereEqualTo("productIdx", productIdx)
        val querySnapshot = query.get().await()

        for (documentSnapshot in querySnapshot) {
            val product = documentSnapshot.toObject(Product::class.java)
            return product
        }

        throw NoSuchElementException("Product with productIdx $productIdx not found")
    }
}