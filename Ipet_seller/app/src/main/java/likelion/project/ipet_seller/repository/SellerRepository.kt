package likelion.project.ipet_seller.repository

import android.content.Context
import likelion.project.ipet_seller.db.local.DataStoreDataSource
import likelion.project.ipet_seller.db.remote.SellerDataSource
import likelion.project.ipet_seller.model.Seller

class SellerRepository(context: Context) {
    private val sellerDataSource = SellerDataSource()
    private val dataStoreDataSource = DataStoreDataSource(context)

    suspend fun getSellerInfo(seller: Seller) = sellerDataSource.getSellerInfo(seller)
    suspend fun signupSeller(seller: Seller) = sellerDataSource.signupSeller(seller)

    suspend fun saveSellerIdToLocal(sellerId: String, sellerPw: String) {
        dataStoreDataSource.writeDataSource(SELLER_ID_KEY, sellerId)
        dataStoreDataSource.writeDataSource(SELLER_PW_KEY, sellerPw)
    }

    suspend fun readSellerIdToLocal(): Pair<String, String> {
        val id = dataStoreDataSource.readDataSource(SELLER_ID_KEY) ?: ""
        val pw = dataStoreDataSource.readDataSource(SELLER_PW_KEY) ?: ""
        return id to pw
    }

    suspend fun removeSellerToLocal() {
        dataStoreDataSource.removeDataSource(SELLER_ID_KEY)
        dataStoreDataSource.removeDataSource(SELLER_PW_KEY)
    }

    companion object {
        private const val SELLER_ID_KEY = "SELLER_ID"
        private const val SELLER_PW_KEY = "SELLER_PW"
    }
}