package likelion.project.ipet_seller.ui.registration

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import likelion.project.ipet_seller.R
import likelion.project.ipet_seller.databinding.FragmentRegistrationBinding
import likelion.project.ipet_seller.ui.main.MainActivity

class RegistrationFragment : Fragment() {

    lateinit var fragmentRegistrationBinding: FragmentRegistrationBinding
    lateinit var mainActivity: MainActivity

    lateinit var albumActivityLauncher: ActivityResultLauncher<Intent>

    val permissionList = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION
    )

    // 현재 선택된 ImageButton을 저장하는 변수
    private var selectedImageButton: ImageButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRegistrationBinding = FragmentRegistrationBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        requestPermissions(permissionList, 0)

        val contract1 = ActivityResultContracts.StartActivityForResult()
        albumActivityLauncher = registerForActivityResult(contract1){

            if(it?.resultCode == AppCompatActivity.RESULT_OK){
                val uri = it.data?.data

                if(uri != null){
                    val bitmap: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val source = ImageDecoder.createSource(mainActivity.contentResolver, uri)
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        val cursor = mainActivity.contentResolver.query(uri, null, null, null, null)
                        cursor?.use {
                            it.moveToNext()
                            val idx = it.getColumnIndex(MediaStore.Images.Media.DATA)
                            val source = it.getString(idx)
                            BitmapFactory.decodeFile(source)
                        }
                    }
                    selectedImageButton?.setImageBitmap(bitmap)
                }

            }
        }

        fragmentRegistrationBinding.run {
            toolbarRegistration.run {
                title = "판매 상품 등록"
                setNavigationIcon(R.drawable.ic_back_24dp)
                inflateMenu(R.menu.menu_registration)

                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.REGISTRATION_FRAGMENT)
                }
            }

            imageButtonRegistration.run {
                setOnClickListener {
                    selectedImageButton = this
                    launchAlbumPicker()
                }
            }

            imageButton2Registration.run {
                setOnClickListener {
                    selectedImageButton = this
                    launchAlbumPicker()
                }
            }

            imageButton3Registration.run {
                setOnClickListener {
                    selectedImageButton = this
                    launchAlbumPicker()
                }
            }

            imageButton4Registration.run {
                setOnClickListener {
                    selectedImageButton = this
                    launchAlbumPicker()
                }
            }

            imageButton5Registration.run {
                setOnClickListener {
                    selectedImageButton = this
                    launchAlbumPicker()
                }
            }

            imageButton6Registration.run {
                setOnClickListener {
                    selectedImageButton = this
                    launchAlbumPicker()
                }
            }

            imageButton7Registration.run {
                setOnClickListener {
                    selectedImageButton = this
                    launchAlbumPicker()
                }
            }

            imageButton8Registration.run {
                setOnClickListener {
                    selectedImageButton = this
                    launchAlbumPicker()
                }
            }
        }


        return fragmentRegistrationBinding.root
    }

    private fun launchAlbumPicker() {
        val newIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        newIntent.type = "image/*"
        val mimeType = arrayOf("image/*")
        newIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
        albumActivityLauncher.launch(newIntent)
    }


}