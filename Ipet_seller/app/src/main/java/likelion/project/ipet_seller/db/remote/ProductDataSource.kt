package likelion.project.ipet_seller.db.remote

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import likelion.project.ipet_seller.constant.CollectionId.PRODUCT_COLLECTION
import likelion.project.ipet_seller.model.Product
import java.io.File

class ProductDataSource {
    private val db = Firebase.firestore
    private val storageRef = Firebase.storage.reference
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
    
    suspend fun registrerProduct(product: Product): Flow<Result<Boolean>> {
        return flow {
            kotlin.runCatching {
                val uploadedImages = mutableListOf<String>()
                val productIdx = db.collection(PRODUCT_COLLECTION).document().id
                product.productImg.forEach {
                    var file = Uri.parse(it)
                    val uploadTask = storageRef.child("productImages/${productIdx}/${file.lastPathSegment}.png")
                        .putFile(file)
                        .await()
                    val downloadUrl = uploadTask.storage.downloadUrl.await()
                    uploadedImages.add(downloadUrl.toString())
                }
                val updatedProduct = product.copy(productIdx = productIdx ,productImg = uploadedImages)
                db.collection(PRODUCT_COLLECTION)
                    .document(productIdx)
                    .set(updatedProduct)
                    .await()
            }.onSuccess {
                emit(Result.success(true))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }
}