package likelion.project.ipet_seller.db.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import likelion.project.ipet_seller.constant.CollectionId.PRODUCT_COLLECTION
import likelion.project.ipet_seller.model.Product

class ProductDataSource {
    private val db = Firebase.firestore

    suspend fun getProducts(productSeller: String): Flow<Result<List<Product>>> {
        return flow {
            runCatching {
                db.collection(PRODUCT_COLLECTION)
                    .whereEqualTo("productSeller", productSeller)
                    .get()
                    .await()
                    .toObjects(Product::class.java)
            }.onSuccess {
                emit(Result.success(it))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }

    suspend fun deleteProducts(product: Product): Flow<Result<Boolean>> {
        return flow {
            runCatching {
                db.collection(PRODUCT_COLLECTION)
                    .document(product.productIdx)
                    .delete()
                    .await()
            }.onSuccess {
                emit(Result.success(true))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }
}