package likelion.project.ipet_seller.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import likelion.project.ipet_seller.R
import likelion.project.ipet_seller.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    lateinit var fragmentSignupBinding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSignupBinding = FragmentSignupBinding.inflate(inflater)
        return fragmentSignupBinding.root
    }
}