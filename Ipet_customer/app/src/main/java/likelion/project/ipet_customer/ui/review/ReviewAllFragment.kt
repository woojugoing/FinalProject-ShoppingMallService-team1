package likelion.project.ipet_customer.ui.review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentReviewAllBinding
import likelion.project.ipet_customer.ui.main.MainActivity


class ReviewAllFragment : Fragment() {

    lateinit var fragmentReviewAllBinding: FragmentReviewAllBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentReviewAllBinding = FragmentReviewAllBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentReviewAllBinding.run {

            materialToolbarReviewAll.run {
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
            }

        }

        return fragmentReviewAllBinding.root
    }


}