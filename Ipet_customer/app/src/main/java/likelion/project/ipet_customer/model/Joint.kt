package likelion.project.ipet_customer.model

data class Joint (
    val jointAnimalType : String = "",
    val jointIdx : String = "",
    val jointImg : ArrayList<*> = arrayListOf<Any>(),
    val jointMember : Long = 0,
    val jointPrice: Long = 0,
    val jointSeller : String = "",
    val jointTerm : String = "",
    val jointText : String = "",
    val jointTitle : String = "",
    val jointTotalMember : Long = 0
)