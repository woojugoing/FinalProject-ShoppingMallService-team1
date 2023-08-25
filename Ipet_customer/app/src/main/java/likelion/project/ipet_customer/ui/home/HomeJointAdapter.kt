package likelion.project.ipet_customer.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.ItemProductCardBinding
import likelion.project.ipet_customer.model.Joint
import likelion.project.ipet_customer.ui.main.MainActivity

class HomeJointAdapter(private val context: MainActivity, val jointsList: MutableList<Joint>): RecyclerView.Adapter<HomeJointAdapter.JointViewHolder>(){

    inner class JointViewHolder(private val binding: ItemProductCardBinding) : RecyclerView.ViewHolder(binding.root){
        var linearLayoutAddMember: LinearLayout = binding.linearLayoutItemAddMember
        var linearLayoutAddCostPrice: LinearLayout = binding.linearLayoutItemAddCostPrice
        var linearLayoutAddTerm: LinearLayout = binding.linearLayoutItemAddTerm

        var imageViewHeart: ImageView = binding.imageViewCardHeart
        var itemJointTerm: TextView = TextView(binding.root.context)
        var itemJointImg: ImageView = binding.imageViewCardThumbnail
        var itemJointTitle: TextView = binding.textViewCardTitle
        var itemJointMember: TextView = TextView(binding.root.context)
        var itemJointMemberIc: ImageView = ImageView(binding.root.context)
        var itemJointPrice: TextView = binding.textViewCardCost
        var itemJointCostPrice: TextView = TextView(binding.root.context)

        init {
            // 추가되는 View들의 test data
            itemJointTerm.text = "08.10 ~ 09.23"
            itemJointMember.text = "9/100"
            itemJointCostPrice.text = "12,000원"
            itemJointMemberIc.setImageResource(R.drawable.ic_groups_24dp)

            // View 추가 및 제거
            linearLayoutAddMember.addView(itemJointMemberIc)
            linearLayoutAddMember.addView(itemJointMember)
            linearLayoutAddCostPrice.addView(itemJointCostPrice)
            linearLayoutAddTerm.addView(itemJointTerm)
            imageViewHeart.visibility = View.GONE

            // 공동 구매 상품 클릭 시
            binding.root.setOnClickListener {
                context.replaceFragment(MainActivity.PRODUCT_INFO_FRAGMENT, true, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JointViewHolder {
        val binding = ItemProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val jointViewHolder = JointViewHolder(binding)

        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // linearLayout height 조정
        binding.linearLayoutItemAddTerm.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        return jointViewHolder
    }

    override fun getItemCount(): Int = jointsList.size

    override fun onBindViewHolder(holder: JointViewHolder, position: Int) {

        val joint = jointsList[position] // 해당 포지션의 Joint 객체 가져오기

        // 스타일 변경 코드들
        holder.itemJointCostPrice.typeface = ResourcesCompat.getFont(holder.itemView.context, R.font.pretendard_regular)

        holder.itemJointCostPrice.textSize = 12f
        holder.itemJointCostPrice.setTextColor(ContextCompat.getColor(context, R.color.gray))
        holder.itemJointCostPrice.paintFlags = holder.itemJointCostPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        val jointMember = holder.itemJointMember
        jointMember.setTextAppearance(R.style.Typography_Regular12)

        val jointMemberIc = holder.itemJointMemberIc
        jointMemberIc.setColorFilter(ContextCompat.getColor(context, R.color.brown_200))

        // img 추가 필요
        holder.itemJointTitle.text = joint.jointTitle
        holder.itemJointMember.text = "${joint.jointMember}/${joint.jointTotalMember}"
        holder.itemJointTerm.text = joint.jointTerm
        holder.itemJointPrice.text = "${joint.jointPrice}원"
    }
}