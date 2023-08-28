package likelion.project.ipet_customer.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import likelion.project.ipet_customer.db.local.SearchDB
import likelion.project.ipet_customer.model.Search
import likelion.project.ipet_customer.repository.SearchRepository

class SearchViewModel(application: Application): AndroidViewModel(application) {

    val getAllSearches: LiveData<List<Search>>
    private val repository: SearchRepository

    init {
        val searchDAO = SearchDB.getDB(application)!!.searchDAO()
        repository = SearchRepository(searchDAO)
        getAllSearches = repository.getAllSearches()
    }

    fun insertSearch(search: Search){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(search)
        }
    }

    class Factory(val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(application) as T
        }
    }

}