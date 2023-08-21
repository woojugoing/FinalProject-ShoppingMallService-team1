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
                require(seller.sellerId.isNotEmpty()) { throw Exception(ID_EMPTY_MESSAGE)}
                require(seller.sellerPw.isNotEmpty()) { throw Exception(PW_EMPTY_MESSAGE)}
                sellers.find { it.sellerId == seller.sellerId && it.sellerPw == seller.sellerPw } ?: throw Exception(INVALID_CREDENTIALS)
            }.onSuccess {
                emit(Result.success(it))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }

    companion object {
        private val ID_EMPTY_MESSAGE = "아이디를 입력해주세요"
        private val PW_EMPTY_MESSAGE = "비밀번호를 입력해주세요"
        private val INVALID_CREDENTIALS = "아이디 또는 비밀번호가 잘못되었습니다"
    }
}