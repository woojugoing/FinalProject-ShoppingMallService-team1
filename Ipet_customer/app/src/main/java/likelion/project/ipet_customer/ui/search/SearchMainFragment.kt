package likelion.project.ipet_customer.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import likelion.project.ipet_customer.databinding.FragmentSearchMainBinding
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
            searchBarSearch.run {

            }
        }

        return fragmentSearchMainBinding.root
    }
}