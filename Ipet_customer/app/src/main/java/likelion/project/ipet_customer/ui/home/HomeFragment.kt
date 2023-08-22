package likelion.project.ipet_customer.ui.home

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentHomeBinding
import likelion.project.ipet_customer.databinding.ItemProductCardBinding
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
            }

            recyclerViewHomeBest.run {
                adapter = BestAdapter()
            }
        }

        return fragmentHomeBinding.root
    }

    inner class JointAdapter() : RecyclerView.Adapter<JointAdapter.JointViewHolder>(){
        inner class JointViewHolder(binding: ItemProductCardBinding) : RecyclerView.ViewHolder(binding.root){
            var cardView : CardView
            var linearLayoutAddMember : LinearLayout
            var linearLayoutAddCostPrice : LinearLayout
            var linearLayoutAddTerm : LinearLayout
            var imageViewHeart : ImageView
            var itemJointTerm : TextView
            var itemJointImg : ImageView
            var itemJointTitle : TextView
            var itemJointMember : TextView
            var itemJointMemberIc : ImageView
            var itemJointPrice : TextView
            var itemJointCostPrice : TextView

            init {
                val textViewJointTerm = TextView(requireContext())
                val textViewJointMember = TextView(requireContext())
                val textViewJointCostPrice = TextView(requireContext())
                val imageViewMember = ImageView(requireContext())

                linearLayoutAddMember = binding.linearLayoutItemAddMember
                linearLayoutAddCostPrice = binding.linearLayoutItemAddCostPrice
                linearLayoutAddTerm = binding.linearLayoutItemAddTerm
                imageViewHeart = binding.imageViewCardHeart
                cardView = binding.cardViewItemProduct
                itemJointImg = binding.imageViewCardThumbnail
                itemJointTitle = binding.textViewCardTitle
                itemJointPrice = binding.textViewCardCost
                itemJointTerm = textViewJointTerm
                itemJointMember = textViewJointMember
                itemJointMemberIc = imageViewMember
                itemJointCostPrice = textViewJointCostPrice

                // 추가되는 View들의 test data(이거 없이 데이터베이스의 데이터를 바로 가져와서 해도 문제 없다면 지워도 됩니다)
                textViewJointTerm.text = "08.10 ~ 09.23"
                textViewJointMember.text = "9/100"
                textViewJointCostPrice.text = "12,000원"
                imageViewMember.setImageResource(R.drawable.ic_groups_24dp)

                // View 추가 및 제거
                linearLayoutAddMember.addView(itemJointMemberIc)
                linearLayoutAddMember.addView(itemJointMember)
                linearLayoutAddCostPrice.addView(itemJointCostPrice)
                linearLayoutAddTerm.addView(itemJointTerm)
                imageViewHeart.visibility = View.GONE

                // 공동 구매 상품 클릭 시
                binding.root.setOnClickListener {

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JointViewHolder {
            val binding = ItemProductCardBinding.inflate(layoutInflater)
            val jointViewHolder = JointViewHolder(binding)

            binding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            // linearLayout height 조정
            binding.linearLayoutItemAddTerm.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

            return jointViewHolder
        }

        override fun getItemCount(): Int = 5

        override fun onBindViewHolder(holder: JointViewHolder, position: Int) {

            // 스타일 변경 코드들
            holder.itemJointCostPrice.typeface = ResourcesCompat.getFont(holder.itemView.context, R.font.pretendard_regular)

            holder.itemJointCostPrice.textSize = 12f
            holder.itemJointCostPrice.setTextColor(ContextCompat.getColor(mainActivity, R.color.gray))
            holder.itemJointCostPrice.paintFlags = holder.itemJointCostPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            val jointMember = holder.itemJointMember
            jointMember.setTextAppearance(R.style.Typography_Regular12)

            val jointMemberIc = holder.itemJointMemberIc
            jointMemberIc.setColorFilter(ContextCompat.getColor(mainActivity, R.color.brown_200))

            // 데이터 변경 코드는 밑에 추가하면 될 거 같아요!
        }
    }

    inner class BestAdapter() : RecyclerView.Adapter<BestAdapter.BestViewHolder>(){
        inner class BestViewHolder(binding: ItemProductCardBinding) : RecyclerView.ViewHolder(binding.root){
            var linearLayout : LinearLayout
            var itemBestTitle : TextView
            var itemBestPrice : TextView
            var itemBestImg : ImageView
            var itemBestRank : TextView

            init {
                val textViewBestRank = TextView(requireContext())

                linearLayout = binding.linearLayoutItemAddRank
                itemBestTitle = binding.textViewCardTitle
                itemBestPrice = binding.textViewCardCost
                itemBestImg = binding.imageViewCardThumbnail
                itemBestRank = textViewBestRank

                textViewBestRank.text = "1위"
                linearLayout.addView(itemBestRank,0)

                // 상품 클릭 시 이벤트
                binding.root.setOnClickListener {

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestViewHolder {
            val binding = ItemProductCardBinding.inflate(layoutInflater)
            val bestViewHolder = BestViewHolder(binding)

            binding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            binding.linearLayoutItemAddRank.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

            return bestViewHolder
        }

        override fun getItemCount(): Int = 10

        override fun onBindViewHolder(holder: BestViewHolder, position: Int) {
            // 스타일 변경 코드
            holder.itemBestRank.typeface = ResourcesCompat.getFont(holder.itemView.context, R.font.pretendard_bold)
            holder.itemBestRank.setTextColor(ContextCompat.getColor(mainActivity, R.color.black))
            holder.itemBestRank.textSize = 12f

            holder.itemBestRank.text = "${position+1}위"
        }
    }
}