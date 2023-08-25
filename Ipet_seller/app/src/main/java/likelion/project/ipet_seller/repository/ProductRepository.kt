package likelion.project.ipet_seller.repository

import android.content.Context
import likelion.project.ipet_seller.db.local.DataStoreDataSource
import likelion.project.ipet_seller.db.remote.ProductDataSource
import likelion.project.ipet_seller.db.remote.SellerDataSource

class ProductRepository() {
    private val productDataSource = ProductDataSource()

    suspend fun getProducts(productSeller: String) = productDataSource.getProducts(productSeller)
}