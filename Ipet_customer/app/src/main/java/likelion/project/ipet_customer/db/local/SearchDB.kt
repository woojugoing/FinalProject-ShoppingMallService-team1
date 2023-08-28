package likelion.project.ipet_customer.db.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import likelion.project.ipet_customer.model.Search

@Database(entities = [Search::class], version = 1, exportSchema = false)
abstract class SearchDB: RoomDatabase() {
    abstract fun searchDAO(): SearchDAO

    companion object {
        @Volatile
        private var instance: SearchDB? = null

        fun getDB(context: Context): SearchDB? {
            if(instance == null) {
                synchronized(SearchDB::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SearchDB::class.java,
                        "search_db"
                    ).build()
                }
            }
            return instance
        }
    }
}