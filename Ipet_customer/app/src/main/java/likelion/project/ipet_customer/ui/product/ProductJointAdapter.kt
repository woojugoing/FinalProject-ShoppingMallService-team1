import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import likelion.project.ipet_customer.databinding.ItemProductCardBinding
import likelion.project.ipet_customer.model.Joint
import likelion.project.ipet_customer.ui.main.MainActivity

class ProductJointAdapter(
    private val viewModel: JointViewModel,
    private val mainActivity: MainActivity
) : RecyclerView.Adapter<ProductJointAdapter.Holder>() {

    inner class Holder(private val rowBinding: ItemProductCardBinding) :
        RecyclerView.ViewHolder(rowBinding.root) {
        private val imageViewCardThumbnail: ImageView = rowBinding.imageViewCardThumbnail
        private val textViewCardTitle: TextView = rowBinding.textViewCardTitle
        private val textViewCardCost: TextView = rowBinding.textViewCardCost

        init {
            rowBinding.root.setOnClickListener {
                val bundle = Bundle()
                val readJointIdx = viewModel.getJointList()[adapterPosition].jointIdx
                bundle.putString("readJointIdx", readJointIdx)
                bundle.putString("readToggle", "joint")

                mainActivity.replaceFragment(MainActivity.PRODUCT_INFO_FRAGMENT, true, bundle)
            }
        }

        fun bind(joint: Joint) {
            textViewCardTitle.text = joint.jointTitle
            textViewCardCost.text = "${joint.jointPrice}원"
            if (joint.jointImg[0] != "") {
                Glide.with(itemView)
                    .load(joint.jointImg[0])
                    .into(imageViewCardThumbnail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val rowBinding = ItemProductCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        rowBinding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return Holder(rowBinding)
    }

    override fun getItemCount(): Int {
        return viewModel.getJointList().size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val joint = viewModel.getJointList()[position]
        holder.bind(joint)
    }
}
