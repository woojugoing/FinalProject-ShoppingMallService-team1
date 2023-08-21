package likelion.project.ipet_seller.repository

import likelion.project.ipet_seller.db.remote.SellerDataSource
import likelion.project.ipet_seller.model.Seller

class SellerRepository {
    private val sellerDataSource = SellerDataSource()

    suspend fun getSellerInfo(seller: Seller) = sellerDataSource.getSellerInfo(seller)
    suspend fun signupSeller(seller: Seller) = sellerDataSource.signupSeller(seller)
}