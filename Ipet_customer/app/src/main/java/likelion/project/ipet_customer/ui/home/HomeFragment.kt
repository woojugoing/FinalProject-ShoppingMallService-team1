package likelion.project.ipet_customer.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.chip.ChipGroup
import com.google.android.material.divider.MaterialDividerItemDecoration
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentHomeBinding
import likelion.project.ipet_customer.databinding.ItemJointProductBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class HomeFragment : Fragment() {

    lateinit var fragmentHomeBinding: FragmentHomeBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentHomeBinding.run {

            // 시작 시 강아지 버튼 선택으로 지정하여 색상 변경
            buttonHomeDog.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.rose_200))

            // 좌측 상단 toggle button
            toggleButtonHome.run {
                addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
                    if (isChecked){
                        when(checkedId){
                            // 강아지 버튼 클릭 시
                            R.id.button_home_dog -> {
                                buttonHomeDog.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.rose_200))
                                buttonHomeCat.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.white))
                            }

                            // 고양이 버튼 클릭 시
                            R.id.button_home_cat -> {
                                buttonHomeCat.setBackgroundColor(ContextCompat.getColor(mainActivity,R.color.rose_200))
                                buttonHomeDog.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.white))
                            }
                        }
                    }
                }
            }

            // 대분류 카테고리 선택 시 UI 변경
            chipGroupHomeLcategory.run {
                buttonHomeScategory6.visibility = View.INVISIBLE

                setOnCheckedStateChangeListener { group, checkedIds ->
                    // test
                    when(this.checkedChipId){
                        R.id.chip_home_food -> {
                            buttonHomeScategory6.visibility = View.INVISIBLE
                        }
                        R.id.chip_home_snack -> {
                            buttonHomeScategory5.visibility = View.INVISIBLE
                        }
                        R.id.chip_home_toy -> {
                            buttonHomeScategory4.visibility = View.INVISIBLE
                        }
                        R.id.chip_home_clothes -> {
                            buttonHomeScategory3.visibility = View.INVISIBLE
                        }
                        R.id.chip_home_house -> {
                            buttonHomeScategory2.visibility = View.INVISIBLE
                        }
                    }
                }
            }

            recyclerViewHomeJoint.run {
                adapter = JointAdapter()

                addItemDecoration(MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.HORIZONTAL))
            }
        }

        return fragmentHomeBinding.root
    }

    inner class JointAdapter() : RecyclerView.Adapter<JointAdapter.JointViewHolder>(){
        inner class JointViewHolder(binding: ItemJointProductBinding) : RecyclerView.ViewHolder(binding.root){
            var itemJointTerm : TextView
            var itemJointImg : ImageView
            var itemJointTitle : TextView
            var itemJointMember : TextView
            var itemJointPrice : TextView
            var itemJointCostPrice : TextView

            init {
                itemJointTerm = binding.textViewJointTerm
                itemJointImg = binding.imageViewJointImg
                itemJointTitle = binding.textViewJointTitle
                itemJointMember = binding.textViewJointMember
                itemJointPrice = binding.textViewJointPrice
                itemJointCostPrice = binding.textViewJointCostPrice

                // 공동 구매 상품 클릭 시
                binding.root.setOnClickListener {

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JointViewHolder {
            val binding = ItemJointProductBinding.inflate(layoutInflater)
            val jointViewHolder = JointViewHolder(binding)

            binding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return jointViewHolder
        }

        override fun getItemCount(): Int = 5

        override fun onBindViewHolder(holder: JointViewHolder, position: Int) {

        }
    }
}