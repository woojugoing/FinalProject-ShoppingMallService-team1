package likelion.project.ipet_customer.ui.search

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import likelion.project.ipet_customer.databinding.FragmentSearchMainBinding
import likelion.project.ipet_customer.databinding.ItemProductCardBinding
import likelion.project.ipet_customer.model.Product
import likelion.project.ipet_customer.model.Search
import likelion.project.ipet_customer.ui.main.MainActivity

class SearchMainFragment : Fragment() {

    lateinit var fragmentSearchMainBinding: FragmentSearchMainBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: SearchViewModel
    val productList = mutableListOf<Product>()
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSearchMainBinding = FragmentSearchMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(this, SearchViewModel.Factory(mainActivity.application)).get(SearchViewModel::class.java)

        fragmentSearchMainBinding.run {
            viewModel.getAllSearches.observe(viewLifecycleOwner, Observer { search ->
                val searchList = search.map { it.searchData }
                val reverseList = searchList.reversed()
                chip.text = reverseList.getOrNull(0) ?: "검색어 1"
                chip2.text = reverseList.getOrNull(1) ?: "검색어 2"
                chip3.text = reverseList.getOrNull(2) ?: "검색어 3"
                chip4.text = reverseList.getOrNull(3) ?: "검색어 4"

                // 칩에 보여줄 검색어를 저장할 리스트
                val chipTexts = mutableListOf<String>()

                // 각 칩에 대해 처리
                for (i in 0 until 4) {
                    val currentChip = when (i) {
                        0 -> chip
                        1 -> chip2
                        2 -> chip3
                        3 -> chip4
                        else -> null
                    }

                    // 현재 칩이 null이 아니고, 해당 칩의 텍스트가 중복되지 않으면 추가
                    if (currentChip != null) {
                        val searchText = reverseList.getOrNull(i)
                        if (searchText != null && !chipTexts.contains(searchText)) {
                            chipTexts.add(searchText)
                            currentChip.text = searchText
                            currentChip.visibility = View.VISIBLE
                        } else {
                            currentChip.visibility = View.GONE
                        }
                    }
                }
            })

            chip.setOnClickListener {
                val searchText = chip.text.toString()
                performSearch(searchText)
            }

            chip2.setOnClickListener {
                val searchText = chip2.text.toString()
                performSearch(searchText)
            }

            chip3.setOnClickListener {
                val searchText = chip3.text.toString()
                performSearch(searchText)
            }

            chip4.setOnClickListener {
                val searchText = chip4.text.toString()
                performSearch(searchText)
            }

            searchViewSearch.run {
                queryHint = "검색어를 입력하세요."
                isSubmitButtonEnabled = true
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (!query.isNullOrBlank()) {
                            performSearch(query.toString())
                            return true
                        }
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText.isNullOrBlank()) {
                            // 검색어가 지워질 때, 기존 레이아웃 표시
                            layoutSearchResult.visibility = View.INVISIBLE
                            layoutSearchNoResult.visibility = View.GONE
                            layoutSearchRecent.visibility = View.VISIBLE
                            layoutSearchBest.visibility = View.VISIBLE

                        }
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

    private fun performSearch(query: String) {
        if (!query.isNullOrBlank()) {
            fragmentSearchMainBinding.layoutSearchRecent.visibility = View.GONE
            fragmentSearchMainBinding.layoutSearchBest.visibility = View.GONE
            val search = Search(0, query)
            viewModel.insertSearch(search)
            productList.clear()
            db.collection("Product")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val title = document["productTitle"] as String
                        if (title.contains(query)) {
                            // 검색 결과를 productList에 추가
                            val idx = document["productIdx"] as String
                            val animalType = document["productAnimalType"] as String
                            val img = document["productImg"] as ArrayList<*>
                            val price = document["productPrice"] as Long
                            val seller = document["productSeller"] as String
                            val text = document["productText"] as String
                            val stock = document["productStock"] as Long
                            val lCategory = document["productLcategory"] as String
                            val sCategory = document["productScategory"] as String

                            val item = Product(
                                animalType, idx, img, lCategory, price, sCategory, seller, stock, text, title
                            )
                            productList.add(item)
                        }
                    }
                    // 검색 결과에 따라 레이아웃을 변경
                    if (productList.isEmpty()) {
                        // 검색 결과가 없는 경우
                        fragmentSearchMainBinding.layoutSearchResult.visibility = View.GONE
                        fragmentSearchMainBinding.layoutSearchNoResult.visibility = View.VISIBLE
                    } else {
                        // 검색 결과가 있는 경우
                        fragmentSearchMainBinding.layoutSearchResult.visibility = View.VISIBLE
                        fragmentSearchMainBinding.layoutSearchNoResult.visibility = View.GONE
                    }

                    // 검색 결과를 업데이트
                    fragmentSearchMainBinding.recyclerViewSearchResult.adapter?.notifyDataSetChanged()
                }
        }
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