package likelion.project.ipet_seller.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import likelion.project.ipet_seller.R
import likelion.project.ipet_seller.databinding.FragmentRegistrationBinding
import likelion.project.ipet_seller.ui.main.MainActivity

class RegistrationFragment : Fragment() {

    lateinit var fragmentRegistrationBinding: FragmentRegistrationBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRegistrationBinding = FragmentRegistrationBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity


        return fragmentRegistrationBinding.root
    }


}