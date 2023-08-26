package likelion.project.ipet_seller.db.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import likelion.project.ipet_seller.constant.CollectionId.ORDER_COLLECTION
import likelion.project.ipet_seller.model.Order
import likelion.project.ipet_seller.ui.order.OrderStatus

class OrderDataSource {
    private val db = Firebase.firestore

    suspend fun getOrders(sellerId: String): Flow<Result<List<Order>>> {
        val orders = db.collection(ORDER_COLLECTION)
            .get()
            .await()
            .toObjects(Order::class.java)
        return flow {
            kotlin.runCatching {
                orders.filter { it.sellerId == sellerId }
            }.onSuccess {
                emit(Result.success(it))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }

    suspend fun getOrdersWithMatchingOrderNumber(sellerId: String): Flow<Result<Map<Long, List<Order>>>> {
        return flow {
            kotlin.runCatching {
                val orders = db.collection(ORDER_COLLECTION)
                    .whereEqualTo("sellerId", sellerId)
                    .get()
                    .await()
                    .toObjects(Order::class.java)
                orders.groupBy { it.orderNumber }
            }.onSuccess {
                emit(Result.success(it))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }

    suspend fun updateOrderStatus(order: Order, orderStatus: OrderStatus): Flow<Result<Boolean>> {
        return flow {
            kotlin.runCatching {
                val orders = db.collection(ORDER_COLLECTION)
                    .whereEqualTo("orderNumber", order.orderNumber)
                    .get()
                    .await()
                orders.forEach { order ->
                    db.collection(ORDER_COLLECTION)
                        .document(order.id)
                        .update("orderState", orderStatus)
                        .await()
                }
            }.onSuccess {
                emit(Result.success(true))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }
}