package likelion.project.ipet_customer.model

data class Review(
    val firebaseKey : String = "",
    val reviewScore : String = "",
    val reviewId : String = "",
    val reviewText : String = "",
    val reviewDate: String = "",
    val reviewImg : String = "",
)
