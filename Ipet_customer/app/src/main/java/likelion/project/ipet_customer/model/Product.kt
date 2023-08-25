package likelion.project.ipet_customer.model

<<<<<<< HEAD
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
=======
data class Product (
    val productAnimalType : String = "",
    val productIdx : Long = 0,
    val productImg : String = "",
    val productLCategory : String = "",
    val productPrice : Long = 0,
    val productSCategory : String = "",
    val productSeller : String = "",
    val productStock : Long = 0,
    val productText : String = "",
    val productTitle : String = ""
)
>>>>>>> origin/develop
