package likelion.project.ipet_seller.db.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStoreDataSource(val context: Context) {
    suspend fun readDataSource(key: String): String? {
        val dataStringKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[dataStringKey]
    }

    suspend fun writeDataSource(key: String, value: String) {
        val dataStringKey = stringPreferencesKey(key)
        context.dataStore.edit {
            it[dataStringKey] = value
        }
    }

    suspend fun removeDataSource(key: String) {
        val dataStringKey = stringPreferencesKey(key)
        context.dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private const val DATASTORE_NAME = "IPET"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)
    }
}