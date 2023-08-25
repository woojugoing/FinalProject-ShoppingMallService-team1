package likelion.project.ipet_customer.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import likelion.project.ipet_customer.model.Joint

class JointRepository {
    private val db = Firebase.firestore

    suspend fun getAllJoint(): MutableList<Joint> {
        val querySnapshot = db.collection("Joint").get().await()
        return querySnapshot.toObjects(Joint::class.java)
    }
}