package likelion.project.ipet_customer.ui.heart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentHeartBinding

class HeartFragment : Fragment() {

    lateinit var fragmentHeartBinding: FragmentHeartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHeartBinding = FragmentHeartBinding.inflate(layoutInflater)

        fragmentHeartBinding.run {

        }

        return fragmentHeartBinding.root
    }
}