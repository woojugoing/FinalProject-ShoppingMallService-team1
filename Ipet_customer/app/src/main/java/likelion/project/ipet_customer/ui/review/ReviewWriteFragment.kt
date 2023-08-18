package likelion.project.ipet_customer.ui.review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentReviewWriteBinding

class ReviewWriteFragment : Fragment() {

    lateinit var fragmentReviewWriteBinding: FragmentReviewWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentReviewWriteBinding = FragmentReviewWriteBinding.inflate(layoutInflater)

        return fragmentReviewWriteBinding.root
    }
}