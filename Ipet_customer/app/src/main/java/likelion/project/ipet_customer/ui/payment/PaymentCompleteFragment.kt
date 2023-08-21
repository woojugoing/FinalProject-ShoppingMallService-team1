package likelion.project.ipet_customer.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import likelion.project.ipet_customer.databinding.FragmentPaymentCompleteBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class PaymentCompleteFragment : Fragment() {

    lateinit var fragmentPaymentCompleteBinding: FragmentPaymentCompleteBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentPaymentCompleteBinding = FragmentPaymentCompleteBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentPaymentCompleteBinding.run {

        }

        return fragmentPaymentCompleteBinding.root
    }

}