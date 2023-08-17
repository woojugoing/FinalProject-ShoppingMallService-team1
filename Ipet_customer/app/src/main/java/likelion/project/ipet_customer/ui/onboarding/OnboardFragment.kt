package likelion.project.ipet_customer.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentOnboardBinding

class OnboardFragment : Fragment() {

    lateinit var fragmentOnboardBinding: FragmentOnboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOnboardBinding = FragmentOnboardBinding.inflate(inflater)
        return fragmentOnboardBinding.root
    }
}