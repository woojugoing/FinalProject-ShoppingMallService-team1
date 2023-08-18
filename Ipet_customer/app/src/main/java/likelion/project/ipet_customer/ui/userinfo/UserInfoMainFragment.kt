package likelion.project.ipet_customer.ui.userinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentUserInfoMainBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class UserInfoMainFragment : Fragment() {

    lateinit var fragmentUserInfoMainBinding: FragmentUserInfoMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentUserInfoMainBinding = FragmentUserInfoMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        fragmentUserInfoMainBinding.run {
            toolbarUserInfoMain.run {
                title = "내 정보"
                setNavigationIcon(R.drawable.ic_back_24dp)
            }
        }

        return fragmentUserInfoMainBinding.root
    }
}