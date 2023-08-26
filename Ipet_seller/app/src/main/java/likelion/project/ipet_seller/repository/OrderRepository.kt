package likelion.project.ipet_seller.repository

import likelion.project.ipet_seller.db.remote.OrderDataSource
import likelion.project.ipet_seller.model.Order
import likelion.project.ipet_seller.ui.order.OrderStatus

class OrderRepository {
    private val orderDataSource = OrderDataSource()

    suspend fun getOrders(sellerId: String) = orderDataSource.getOrders(sellerId)
    suspend fun getOrdersWithMatchingOrderNumber(sellerId: String) = orderDataSource.getOrdersWithMatchingOrderNumber(sellerId)
    suspend fun updateOrderStatus(order: Order, orderStatus: OrderStatus) = orderDataSource.updateOrderStatus(order, orderStatus)
}