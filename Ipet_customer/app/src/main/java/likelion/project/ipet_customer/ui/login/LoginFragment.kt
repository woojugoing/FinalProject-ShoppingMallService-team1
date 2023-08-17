package likelion.project.ipet_customer.ui.login

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)

        fragmentLoginBinding.run{
            // 제목
            textViewLoginTitle1.run{
                setTextColor(resources.getColor(R.color.brown_200))
            }

            // 부제목
            textViewLoginTitle2.run{
                setTextAppearance(R.style.Typography_Bold24)
                setTextColor(resources.getColor(R.color.rose_200))
            }
        }
        return fragmentLoginBinding.root
    }
}