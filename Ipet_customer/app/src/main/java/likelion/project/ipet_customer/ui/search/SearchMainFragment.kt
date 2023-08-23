package likelion.project.ipet_customer.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import likelion.project.ipet_customer.databinding.FragmentSearchMainBinding
import likelion.project.ipet_customer.databinding.ItemProductCardBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class SearchMainFragment : Fragment() {

    lateinit var fragmentSearchMainBinding: FragmentSearchMainBinding
    lateinit var mainActivity: MainActivity

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
                        layoutSearchRecent.visibility = View.GONE
                        layoutSearchBest.visibility = View.GONE
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        TODO("Not yet implemented")
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
            return 10
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.textViewCardTitle.text = "${position+1} 번째 사료"
            holder.textViewCardCost.text = "${position+1}0000원"
        }
    }
}