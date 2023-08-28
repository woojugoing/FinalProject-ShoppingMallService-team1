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

    suspend fun getOneJoint(jointIdx: Long): Joint {
        val query = db.collection("Joint").whereEqualTo("jointIdx", jointIdx)
        val querySnapshot = query.get().await()

        for (documentSnapshot in querySnapshot) {
            val joint = documentSnapshot.toObject(Joint::class.java)
            return joint
        }

        throw NoSuchElementException("Product with productIdx $jointIdx not found")
    }
}