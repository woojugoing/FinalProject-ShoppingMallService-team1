package likelion.project.ipet_customer.ui.review

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentReviewAllBinding
import likelion.project.ipet_customer.databinding.ItemReviewAllReviewBinding
import likelion.project.ipet_customer.model.Review
import likelion.project.ipet_customer.ui.main.MainActivity


class ReviewAllFragment : Fragment() {

    lateinit var fragmentReviewAllBinding: FragmentReviewAllBinding
    lateinit var mainActivity: MainActivity

    val reviewDataList = mutableListOf<Review>()
    val db = Firebase.firestore

    var readProductIdx = ""
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

            readProductIdx = arguments?.getString("readProductIdx")!!

            db.collection("Review")
                .whereEqualTo("firebaseKey", readProductIdx)    // productInfoFragment 에서, readProductIdx 받아오기
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val firebaseKey = document["firebaseKey"] as String
                        val score = document["reviewScore"] as String
                        val id = document["reviewId"] as String
                        val test = document["reviewText"] as String
                        val date = document["reviewDate"] as String
                        val img = document["reviewImg"] as String

                        val item = Review(firebaseKey, score, id, test, date, img)
                        reviewDataList.add(item)
                        Log.d("reviewDataList", "reviewDataList : ${reviewDataList}")
                    }

                    // 리뷰 전체보기 화면의 처음 리사이클러뷰를 최신순으로 지정
                    // reviewDate를 기준으로 reviewDataList를 내림차순으로 정렬한다 (최신순이 제일 위로오개함)
                    reviewDataList.sortByDescending { it.reviewDate }
                    fragmentReviewAllBinding.recyclerReviewAll.adapter?.notifyDataSetChanged()

                }
                .addOnFailureListener { exception ->
                    Log.d("reviewDataList", "Error getting documents: ", exception)
                }

            // 최신순으로 보기
            textViewReviewAllNewest.setOnClickListener {
                val layoutParams = viewReviewAllLine.layoutParams as LinearLayout.LayoutParams
                layoutParams.leftMargin = textViewReviewAllNewest.left
                viewReviewAllLine.layoutParams = layoutParams

                reviewDataList.sortByDescending { it.reviewDate }
                fragmentReviewAllBinding.recyclerReviewAll.adapter?.notifyDataSetChanged()
            }

            // 평점순으로 보기
            textViewReviewAllRating.setOnClickListener {
                val layoutParams = viewReviewAllLine.layoutParams as LinearLayout.LayoutParams
                layoutParams.leftMargin = textViewReviewAllRating.left
                viewReviewAllLine.layoutParams = layoutParams

                reviewDataList.sortByDescending { it.reviewScore.toFloat() }
                fragmentReviewAllBinding.recyclerReviewAll.adapter?.notifyDataSetChanged()
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
            return reviewDataList.size
        }

        override fun onBindViewHolder(holder: ReviewAllHolder, position: Int) {

            // 원하는 색상을 리소스에 가져온 다음 RatingBar 에 적용
            val starColor = ContextCompat.getColor(holder.itemView.context, R.color.rose_200)
            holder.ratingBarReviewAllReview.progressTintList = ColorStateList.valueOf(starColor)

            holder.ratingBarReviewAllReview.rating = reviewDataList[position].reviewScore.toFloat()
            holder.textViewReviewAllUserName.text = reviewDataList[position].reviewId
            holder.textViewReviewAllDate.text = reviewDataList[position].reviewDate
            holder.textViewReviewAllReviewContents.text = reviewDataList[position].reviewText

            // 리뷰 이미지 확인
            if (reviewDataList[position].reviewImg.isEmpty()) {
                holder.imageViewReviewAllReviewImage.visibility = View.GONE
            } else {
                holder.imageViewReviewAllReviewImage.visibility = View.VISIBLE

            }

        }

    }


}