package likelion.project.ipet_customer.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import likelion.project.ipet_customer.databinding.FragmentSearchMainBinding
import likelion.project.ipet_customer.databinding.ItemProductCardBinding
import likelion.project.ipet_customer.model.Product
import likelion.project.ipet_customer.ui.main.MainActivity

class SearchMainFragment : Fragment() {

    lateinit var fragmentSearchMainBinding: FragmentSearchMainBinding
    lateinit var mainActivity: MainActivity
    val productList = mutableListOf<Product>()
    var searchResult = ""
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSearchMainBinding = FragmentSearchMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentSearchMainBinding.run {
            chip.text = "검색어 1"
            chip2.text = "검색어 2"
            chip3.text = "검색어 3"
            chip4.text = "검색어 4"

            searchViewSearch.run {
                queryHint = "검색어를 입력하세요."
                isSubmitButtonEnabled = true
                setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (!query.isNullOrBlank()) {
                            Log.d("검색 결과 search", searchViewSearch.query.toString())
                            layoutSearchRecent.visibility = View.GONE
                            layoutSearchBest.visibility = View.GONE

                            productList.clear()

                            db.collection("Product")
                                .get()
                                .addOnSuccessListener { result ->
                                    for (document in result) {
                                        val title = document["productTitle"] as String
                                        if(title.contains("$query")) {
                                            val idx = document["productIdx"] as Long
                                            val animalType = document["productAnimalType"] as String
                                            val img = document["productImg"] as String
                                            val price = document["productPrice"] as Long
                                            val seller = document["productSeller"] as String
                                            val text = document["productText"] as String
                                            val stock = document["productStock"] as Long
                                            val lCategory = document["productLCategory"] as String
                                            val sCategory = document["productSCategory"] as String

                                            val item = Product(
                                                animalType, idx, img, lCategory, price, sCategory, seller, stock, text, title
                                            )
                                            productList.add(item)
                                            fragmentSearchMainBinding.recyclerViewSearchResult.adapter?.notifyDataSetChanged()
                                        }

                                    }
                                }
                            return true
                        }
                        return false
                    }
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
            }

            recyclerViewSearchResult.run {
                adapter = Adapter()
                layoutManager = GridLayoutManager(context, 2)
            }
        }

        return fragmentSearchMainBinding.root
    }

    inner class Adapter: RecyclerView.Adapter<Adapter.Holder>() {
        inner class Holder(rowBinding: ItemProductCardBinding): RecyclerView.ViewHolder(rowBinding.root) {
            val imageViewCardThumbnail: ImageView
            val textViewCardTitle: TextView
            val textViewCardCost: TextView

            init {
                imageViewCardThumbnail = rowBinding.imageViewCardThumbnail
                textViewCardTitle = rowBinding.textViewCardTitle
                textViewCardCost = rowBinding.textViewCardCost
                rowBinding.root.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.PRODUCT_INFO_FRAGMENT, true, null)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val rowBinding = ItemProductCardBinding.inflate(layoutInflater)
            val holder = Holder(rowBinding)

            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            return holder
        }

        override fun getItemCount(): Int {
            return productList.size
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.textViewCardTitle.text = productList[position].productTitle
            holder.textViewCardCost.text = productList[position].productPrice.toString()
        }
    }
}