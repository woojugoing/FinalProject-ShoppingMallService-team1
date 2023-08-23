package likelion.project.ipet_seller.model

data class Order(
    val orderNumber: Int = 0,
    val orderDate: Long = 0L,
    val orderRecipient: String = "",
    val orderState: Int = -1,
    val sellerId: String = "",
)