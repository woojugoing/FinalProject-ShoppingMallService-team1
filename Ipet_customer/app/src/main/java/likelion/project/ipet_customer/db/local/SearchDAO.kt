package likelion.project.ipet_customer.db.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import likelion.project.ipet_customer.model.Search

@Dao
interface SearchDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(searchEntity: Search)

    @Query("SELECT * FROM search_table")
    fun getAllSearches(): LiveData<List<Search>>
}