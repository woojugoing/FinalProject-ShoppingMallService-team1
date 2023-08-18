package likelion.project.ipet_customer.ui.review

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import likelion.project.ipet_customer.R
import likelion.project.ipet_customer.databinding.FragmentReviewWriteBinding
import likelion.project.ipet_customer.ui.main.MainActivity

class ReviewWriteFragment : Fragment() {

    lateinit var fragmentReviewWriteBinding: FragmentReviewWriteBinding
    lateinit var reviewAlbumLauncher : ActivityResultLauncher<Intent>
    lateinit var mainActivity: MainActivity

    // 확인할 권한 목록
    val permissionList = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_MEDIA_LOCATION
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentReviewWriteBinding = FragmentReviewWriteBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        // 권한 확인
        requestPermissions(permissionList, 0)

        val contract1 = ActivityResultContracts.StartActivityForResult()
        reviewAlbumLauncher = registerForActivityResult(contract1){

            if(it?.resultCode == RESULT_OK){

                // 선택한 이미지에 접근할 수 있는 Uri 객체를 추출한다.
                val uri = it.data?.data

                if (uri != null){
                    // 안드로이드 10(Q) 이상이라면
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

                        // 이미지를 생성할 수 있는 디코더를 생성한다.
                        val source = ImageDecoder.createSource(mainActivity.contentResolver, uri)

                        // Bitmap객체를 생성한다.
                        val bitmap = ImageDecoder.decodeBitmap(source)

                        // 메인 화면의 imageView에 해당 이미지를 설정한다.
                        fragmentReviewWriteBinding.imageViewReviewWriteImg.setImageBitmap(bitmap)

                    } else {
                        // Content Provider를 통해 이미지 데이터 정보를 가져온다.
                        val cursor = mainActivity.contentResolver.query(uri,null,null,null,null)

                        if(cursor != null) {
                            cursor.moveToNext()

                            // 이미지의 경로를 가져온다.
                            val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                            val source = cursor.getString(idx)

                            // 이미지를 생성하여 보여준다.
                            val bitmap = BitmapFactory.decodeFile(source)
                            fragmentReviewWriteBinding.imageViewReviewWriteImg.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        }

        fragmentReviewWriteBinding.run {
            imageViewReviewWriteGalleryButton.run {
                setOnClickListener {
                    // 앨범에서 사진을 선택할 수 있는 Activity를 실행한다.
                    val newIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                    // 실행할 액티비티의 MIME 타입 설정(이미지로 설정)
                    newIntent.setType("image/*")

                    // 선택할 파일의 타입을 지정(안드로이드 os가 이미지에 대한 사진 작업을 할 수 있도록)
                    val mineType = arrayOf("image/*")
                    newIntent.putExtra(Intent.EXTRA_MIME_TYPES, mineType)

                    // 액티비티 실행
                    reviewAlbumLauncher.launch(newIntent)
                }
            }
        }

        return fragmentReviewWriteBinding.root
    }
}