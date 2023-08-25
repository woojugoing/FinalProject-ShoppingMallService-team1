package likelion.project.ipet_customer.model

data class Order (
    val orderDate : Long = 0,
    val orderNumber : Long = 0,
    val orderRecipient : String = "",
    val orderState : Long = 0,
    val productIdx : Long = 0,
    val sellerId : String = ""
)