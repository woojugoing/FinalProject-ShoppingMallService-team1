package likelion.project.ipet_customer.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentPaymentBinding
import likelion.project.ipet_customer.databinding.ItemShoppingBasketProductBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class PaymentFragment : Fragment() {

    lateinit var fragmentPaymentBinding: FragmentPaymentBinding
    lateinit var mainActivity: MainActivity

    val couponDataList = arrayOf(
        "쿠폰을 선택해주세요","아이펫 신규 회원 가입 쿠폰", "전체 항목 10% 할인", "항목3", "항목4", "항목5"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentPaymentBinding = FragmentPaymentBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentPaymentBinding.run {

            toolbarPayment.run {
                title = "주문서 작성"
                setNavigationIcon(R.drawable.ic_back_24dp)
            }

            recyclerPaymentProduct.run {
                adapter = PaymentProductAdapter()
                layoutManager = LinearLayoutManager(context)
            }

            spinnerPaymentCoupon.run {

                // 어뎁터 설정
                val counponSpinner = ArrayAdapter<String>(
                    context,
                    // Spinner가 접혀있을 때의 모양
                    android.R.layout.simple_spinner_item,
                    couponDataList
                )
                // Spinner가 펼쳐져 있을 때의 항목 모양
                counponSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapter = counponSpinner

                // 항목을 선택하면 동작하는 리스너
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }

                buttonPayment.run {
                    setOnClickListener {
                        mainActivity.replaceFragment(MainActivity.PAYMENT_COMPLETE_FRAGMENT, false, null)
                    }
                }

            }

        }

        return fragmentPaymentBinding.root
    }

    inner class PaymentProductAdapter : RecyclerView.Adapter<PaymentProductAdapter.PaymentProductViewHolder>() {

        inner class PaymentProductViewHolder(itemShoppingBasketProductBinding: ItemShoppingBasketProductBinding) : RecyclerView.ViewHolder(itemShoppingBasketProductBinding.root) {
            val checkBoxProdut : CheckBox
            val imageViewProduct : ImageView
            val textViewProductName : TextView
            val textViewProductCount : TextView
            val textViewProductPrice : TextView

            init {
                checkBoxProdut = itemShoppingBasketProductBinding.checkBoxProdut
                imageViewProduct = itemShoppingBasketProductBinding.imageViewProduct
                textViewProductName = itemShoppingBasketProductBinding.textViewProductName
                textViewProductCount = itemShoppingBasketProductBinding.textViewProductCount
                textViewProductPrice = itemShoppingBasketProductBinding.textViewProductPrice
            }

        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PaymentProductViewHolder {
            val itemShoppingBasketProductBinding = ItemShoppingBasketProductBinding.inflate(layoutInflater)
            val paymentProductViewHolder = PaymentProductViewHolder(itemShoppingBasketProductBinding)

            itemShoppingBasketProductBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return paymentProductViewHolder
        }

        override fun getItemCount(): Int {
            return 3
        }

        override fun onBindViewHolder(holder: PaymentProductViewHolder, position: Int) {
            holder.checkBoxProdut.visibility = View.GONE

        }
    }

}