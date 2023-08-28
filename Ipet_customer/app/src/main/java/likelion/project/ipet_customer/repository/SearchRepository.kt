package likelion.project.ipet_customer.repository

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import likelion.project.ipet_customer.db.local.SearchDAO
import likelion.project.ipet_customer.model.Product
import likelion.project.ipet_customer.model.Search

class SearchRepository(private val searchDAO: SearchDAO) {
    suspend fun insert(search: Search) {
        searchDAO.insert(search)
    }

    fun getAllSearches(): LiveData<List<Search>> {
        return searchDAO.getAllSearches()
    }
}
