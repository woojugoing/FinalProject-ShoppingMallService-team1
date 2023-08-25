package likelion.project.ipet_customer.model

data class Product(
    val productIdx: Long,
    val productTitle: String,
    val productPrice: Long,
    val productImg: String,
    val productAnimalType: String,
    val productLCategory: String,
    val productSCategory: String,
    val productText: String,
    val productStock: Long,
    val productSeller: String
)
