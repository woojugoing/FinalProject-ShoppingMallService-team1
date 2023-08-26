package likelion.project.ipet_seller.model

data class Product(
    val productIdx: String = "",
    val productTitle: String = "",
    val productPrice: Long = 0L,
    val productImg: String = "",
    val productAnimalType: String = "",
    val productLcategory: List<String> = emptyList(),
    val productScategory: List<String> = emptyList(),
    val productText: String = "",
    val productStock: Long = 0L,
    val productSeller: String = ""
)