package likelion.project.ipet_customer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentPermissionBinding

class PermissionFragment : Fragment() {

    lateinit var fragmentPermissionBinding: FragmentPermissionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPermissionBinding = FragmentPermissionBinding.inflate(inflater)
        return fragmentPermissionBinding.root
    }
}