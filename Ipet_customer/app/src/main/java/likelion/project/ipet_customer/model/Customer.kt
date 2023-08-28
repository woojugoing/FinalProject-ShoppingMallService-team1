package likelion.project.ipet_customer.model

data class Customer(
    // 초기 로그인 단계에서 설정되는 정보
    val customerId: String = "",
    val customerName: String = "",
    val customerEmail: String = "",
    // 기타 필요한 정보
    var customerAddress: String = ""
)