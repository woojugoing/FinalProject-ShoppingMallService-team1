package likelion.project.ipet_customer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_table")
data class Search(
    @PrimaryKey(autoGenerate = true)
    val idx: Int,
    val searchData: String
)
