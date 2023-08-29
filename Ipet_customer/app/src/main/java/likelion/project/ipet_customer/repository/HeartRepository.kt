package likelion.project.ipet_customer.repository

import android.util.Log
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import likelion.project.ipet_customer.model.Heart

class HeartRepository {
    private val db = Firebase.firestore

    suspend fun setAddHeart(heart : Heart){
        withContext(Dispatchers.IO) {
            db.collection("Heart")
                .add(heart)
                .await()
        }
    }

    suspend fun setDeleteHeart(heart: Heart) {
        val query = db.collection("Heart")
            .whereEqualTo("buyerId", heart.buyerId)
            .whereEqualTo("productIdx", heart.productIdx)

        val querySnapshot = query.get().await()

        if (!querySnapshot.isEmpty) {
            val documentSnapshot = querySnapshot.documents.first()
            documentSnapshot.reference.delete()
        }
    }

    fun registerHeartListener(buyerId: String, productIdx: String, listener: (Boolean) -> Unit): ListenerRegistration {
        val query = db.collection("Heart")
            .whereEqualTo("buyerId", buyerId)
            .whereEqualTo("productIdx", productIdx)

        return query.addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                listener(false) // 업데이트 실패 시 호출
                return@addSnapshotListener
            }

            if (querySnapshot != null && !querySnapshot.isEmpty) {
                listener(true) // 데이터가 존재할 때 호출
            } else {
                listener(false) // 데이터가 없을 때 호출
            }
        }
    }
}