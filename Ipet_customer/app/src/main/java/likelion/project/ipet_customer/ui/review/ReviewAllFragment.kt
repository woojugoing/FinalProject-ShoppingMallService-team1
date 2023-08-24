package likelion.project.ipet_customer.ui.review

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentReviewAllBinding
import likelion.project.ipet_customer.databinding.ItemReviewAllReviewBinding
import likelion.project.ipet_customer.ui.main.MainActivity


class ReviewAllFragment : Fragment() {

    lateinit var fragmentReviewAllBinding: FragmentReviewAllBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentReviewAllBinding = FragmentReviewAllBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentReviewAllBinding.run {

            materialToolbarReviewAll.run {
                setNavigationIcon(R.drawable.ic_back_24dp)

                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.REVIEWALL_FRAGMENT)
                }
            }

            textViewReviewAllNewest.setOnClickListener {
                val layoutParams = viewReviewAllLine.layoutParams as LinearLayout.LayoutParams
                layoutParams.leftMargin = textViewReviewAllNewest.left
                viewReviewAllLine.layoutParams = layoutParams
            }

            textViewReviewAllRating.setOnClickListener {
                val layoutParams = viewReviewAllLine.layoutParams as LinearLayout.LayoutParams
                layoutParams.leftMargin = textViewReviewAllRating.left
                viewReviewAllLine.layoutParams = layoutParams
            }

            recyclerReviewAll.run {
                adapter = ReviewAllAdapter()
                layoutManager = LinearLayoutManager(context)

                addItemDecoration(DividerItemDecoration(mainActivity, LinearLayoutManager.VERTICAL))
            }

        }

        return fragmentReviewAllBinding.root
    }

    inner class ReviewAllAdapter : RecyclerView.Adapter<ReviewAllAdapter.ReviewAllHolder>() {

        inner class ReviewAllHolder(itemReviewAllReviewBinding: ItemReviewAllReviewBinding) : RecyclerView.ViewHolder(itemReviewAllReviewBinding.root) {

            val ratingBarReviewAllReview : RatingBar
            val textViewReviewAllUserName : TextView
            val textViewReviewAllDate : TextView
            val imageViewReviewAllReviewImage : ImageView
            val textViewReviewAllReviewContents : TextView

            init {
                ratingBarReviewAllReview = itemReviewAllReviewBinding.ratingBarReviewAllReview
                textViewReviewAllUserName = itemReviewAllReviewBinding.textViewReviewAllUserName
                textViewReviewAllDate = itemReviewAllReviewBinding.textViewReviewAllDate
                imageViewReviewAllReviewImage = itemReviewAllReviewBinding.imageViewReviewAllReviewImage
                textViewReviewAllReviewContents = itemReviewAllReviewBinding.textViewReviewAllReviewContents
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAllHolder {
            val itemReviewAllReviewBinding = ItemReviewAllReviewBinding.inflate(layoutInflater)
            val reviewAllHolder = ReviewAllHolder(itemReviewAllReviewBinding)

            itemReviewAllReviewBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return reviewAllHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: ReviewAllHolder, position: Int) {

            // 원하는 색상을 리소스에 가져온 다음 RatingBar 에 적용
            val starColor = ContextCompat.getColor(holder.itemView.context, R.color.rose_200)
            holder.ratingBarReviewAllReview.progressTintList = ColorStateList.valueOf(starColor)

            holder.ratingBarReviewAllReview.rating = 2.5F
        }

    }


}