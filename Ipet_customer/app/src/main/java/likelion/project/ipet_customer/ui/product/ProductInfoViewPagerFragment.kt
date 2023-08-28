package likelion.project.ipet_customer.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentProductInfoViewPagerBinding

class ProductInfoViewPagerFragment(val image : String) : Fragment() {

    lateinit var fragmentProductInfoViewPagerBinding: FragmentProductInfoViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductInfoViewPagerBinding = FragmentProductInfoViewPagerBinding.inflate(inflater)

        fragmentProductInfoViewPagerBinding.run {
            Glide.with(this@ProductInfoViewPagerFragment)
                .load(image)
                .into(imageViewProductinfoViewpager)
        }

        return fragmentProductInfoViewPagerBinding.root
    }
}