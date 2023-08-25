package likelion.project.ipet_seller.model

data class Order(
    val orderNumber: Long = 0L,
    val orderDate: Long = 0L,
    val orderRecipient: String = "",
    val orderState: Int = -1,
    val sellerId: String = "",
    val productIdx: Long = 0L,
)