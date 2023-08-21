package likelion.project.ipet_seller.db.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import likelion.project.ipet_seller.constant.CollectionId.SELLER_COLLECTION
import likelion.project.ipet_seller.model.Seller

class SellerDataSource {
    private val db = Firebase.firestore

    suspend fun getSellerInfo(seller: Seller): Flow<Result<Seller>> {
        val sellers = db.collection(SELLER_COLLECTION)
            .get()
            .await()
            .toObjects(Seller::class.java)
        return flow {
            kotlin.runCatching {
                sellers.find { it.sellerId == seller.sellerId && it.sellerPw == seller.sellerPw } ?: throw Exception("Seller not found")
            }.onSuccess {
                emit(Result.success(it))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }
}