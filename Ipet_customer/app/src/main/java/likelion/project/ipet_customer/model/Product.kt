package likelion.project.ipet_customer.model

data class Product(
    val productIdx: Long,
    val productPrice: Long,
    val productImg: String,
    val productAnimalType: Long,
    val productLCategory: String,
    val productSCategory: String,
    val productText: String,
    val productStock: Long,
    val productSeller: String
)
