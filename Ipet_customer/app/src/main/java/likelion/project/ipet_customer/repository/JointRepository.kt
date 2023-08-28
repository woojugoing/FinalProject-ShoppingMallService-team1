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

    suspend fun getOneJoint(jointIdx: String): Joint {
        val query = db.collection("Joint").whereEqualTo("jointIdx", jointIdx)
        val querySnapshot = query.get().await()

        for (documentSnapshot in querySnapshot) {
            val joint = documentSnapshot.toObject(Joint::class.java)
            return joint
        }

        throw NoSuchElementException("Product with productIdx $jointIdx not found")
    }

    fun getJointData(callback: (List<Joint>) -> Unit) {
        db.collection("Joint")
            .get()
            .addOnSuccessListener { result ->
                val joinTdataList = mutableListOf<Joint>()
                for (document in result) {
                    val idx = document["jointIdx"] as String
                    val animalType = document["jointAnimalType"] as String
                    val img = document["jointImg"] as ArrayList<String>
                    val member = document["jointMember"] as Long
                    val price = document["jointPrice"] as Long
                    val seller = document["jointSeller"] as String
                    val term = document["jointTerm"] as String
                    val text = document["jointText"] as String
                    val title = document["jointTitle"] as String
                    val totalMember = document["jointTotalMember"] as Long

                    val item = Joint(animalType, idx, img, member, price, seller, term, text, title, totalMember)
                    joinTdataList.add(item)
                }
                callback(joinTdataList)
            }
    }
}