package likelion.project.ipet_customer.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import likelion.project.ipet_customer.model.Review

class ReviewRepository {
    private val db = Firebase.firestore

    suspend fun selectReview(idx : String) : MutableList<Review>{
        val query = db.collection("Review").whereEqualTo("firebaseKey", idx)
        val querySnapshot = query.get().await()

        val reviews = mutableListOf<Review>()

        for (documentSnapshot in querySnapshot) {
            val review = documentSnapshot.toObject(Review::class.java)
            reviews.add(review)
        }

        return reviews
    }
}