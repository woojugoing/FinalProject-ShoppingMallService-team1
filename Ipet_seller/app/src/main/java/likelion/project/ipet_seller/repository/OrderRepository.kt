package likelion.project.ipet_seller.repository

import likelion.project.ipet_seller.db.remote.OrderDataSource

class OrderRepository {
    private val orderDataSource = OrderDataSource()

    suspend fun getOrders(sellerId: String) = orderDataSource.getOrders(sellerId)
}