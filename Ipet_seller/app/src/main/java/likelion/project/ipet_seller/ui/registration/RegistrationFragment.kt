@file:Suppress("UNREACHABLE_CODE")

package likelion.project.ipet_seller.ui.registration

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import likelion.project.ipet_seller.R
import likelion.project.ipet_seller.databinding.FragmentRegistrationBinding
import likelion.project.ipet_seller.model.Product
import likelion.project.ipet_seller.ui.main.MainActivity

class RegistrationFragment : Fragment() {

    lateinit var fragmentRegistrationBinding: FragmentRegistrationBinding
    lateinit var mainActivity: MainActivity

    lateinit var albumActivityLauncher: ActivityResultLauncher<Intent>
    lateinit var viewModel: RegistrationViewModel
    private var product = Product()

    val permissionList = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION
    )

    // 현재 선택된 ImageButton을 저장하는 변수
    private var selectedImageButton: ImageButton? = null

    val animalType = arrayOf("반려동물 선택", "강아지", "고양이")
    val mainCategory = arrayOf("대분류 선택", "사료", "간식", "장난감", "의류", "집")
    var subCategory = arrayOf("소분류 선택", "주니어", "어덜트", "시니어", "다이어트", "건식 사료", "습식 사료")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRegistrationBinding = FragmentRegistrationBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(
            this,
            RegistrationViewModelFactory(mainActivity)
        )[RegistrationViewModel::class.java]

        observe()

        requestPermissions(permissionList, 0)

        val contract1 = ActivityResultContracts.StartActivityForResult()
        val images = mutableListOf<String>()
        albumActivityLauncher = registerForActivityResult(contract1) {

            if (it?.resultCode == AppCompatActivity.RESULT_OK) {
                val uri = it.data?.data

                if (uri != null) {
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
                    images.add(uri.toString())

                    selectedImageButton?.setImageBitmap(bitmap)
                }

            }
        }


        fragmentRegistrationBinding.run {
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

            setupSpinner(spinnerRegistrationAnimalType, animalType) { parent, view, position, id ->
                product = product.copy(productAnimalType = animalType[position])
            }

            setupSpinner(
                spinnerRegistrationMainCategory,
                mainCategory
            ) { parent, view, position, id ->
                product = product.copy(productLcategory = mainCategory[position])
                viewModel.onSpinnerItemClick(position)
            }

            toolbarRegistration.run {
                title = "판매 상품 등록"
                setNavigationIcon(R.drawable.ic_back_24dp)
                inflateMenu(R.menu.menu_registration)

                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.REGISTRATION_FRAGMENT)
                }
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.item_save -> {

                            val price = if (editTextPrice.text.toString().isEmpty()) {
                                0L
                            } else {
                                editTextPrice.text.toString().toLong()
                            }
                            val stock = if (editTextStock.text.toString().isEmpty()) {
                                0L
                            } else {
                                editTextStock.text.toString().toLong()
                            }

                            progressBarRegistraintion.visibility = View.VISIBLE
                            viewModel.onUploadClickEvent(
                                Product(
                                    productTitle = editTextText.text.toString(),
                                    productPrice = price,
                                    productImg = images,
                                    productAnimalType = product.productAnimalType,
                                    productLcategory = product.productLcategory,
                                    productScategory = product.productScategory,
                                    productText = editTextTextMultiLine.text.toString(),
                                    productStock = stock,
                                )
                            )
                            true
                        }

                        else -> false
                    }
                }
            }

        }

        return fragmentRegistrationBinding.root
    }

    private fun observe() {
        lifecycleScope.launch {
            launch {
                viewModel.event.collect {
                    when (it) {
                        is Result.Success -> {
                            showSnackBar(fragmentRegistrationBinding.root, it.message)
                            mainActivity.removeFragment(MainActivity.REGISTRATION_FRAGMENT)
                            fragmentRegistrationBinding.progressBarRegistraintion.visibility =
                                View.GONE
                        }

                        is Result.Failure -> {
                            showSnackBar(fragmentRegistrationBinding.root, it.message)
                            fragmentRegistrationBinding.progressBarRegistraintion.visibility =
                                View.GONE
                        }

                        is Result.Error -> {
                            showSnackBar(fragmentRegistrationBinding.root, it.error.message!!)
                            fragmentRegistrationBinding.progressBarRegistraintion.visibility =
                                View.GONE
                        }
                    }
                }
            }

            launch {
                viewModel.spinnerEvent.collect {
                    val subCategory = getSubCategories(it)
                    setupSpinner(
                        fragmentRegistrationBinding.spinnerRegistrationSubCategory,
                        subCategory
                    ) { parent, view, position, id ->
                        product = product.copy(productScategory = subCategory[position])
                    }
                    delay(200)
                }
            }
        }
    }

    private fun getSubCategories(it: Int) = when (mainCategory[it]) {
        "사료" -> arrayOf("주니어", "어덜트", "시니어", "다이어트", "건식", "습식")
        "간식" -> arrayOf("껌", "스낵", "육포", "캔", "비스켓", "기타")
        "장난감" -> arrayOf("공", "인형", "큐브", "훈련용품", "스크래쳐", "기타")
        "의류" -> arrayOf("레인코트", "신발", "외투", "원피스", "셔츠", "기타")
        "집" -> arrayOf("계단", "매트", "울타리", "안전문", "하우스", "기타")
        else -> arrayOf("대분류를 선택해주세요")
    }

    private fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun launchAlbumPicker() {
        val newIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        newIntent.type = "image/*"
        val mimeType = arrayOf("image/*")
        newIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
        albumActivityLauncher.launch(newIntent)
    }

    // 스피너 설정 메서드
    private fun setupSpinner(
        spinner: Spinner,
        data: Array<String>,
        onItemSelected: (parent: AdapterView<*>?, view: View?, position: Int, id: Long) -> Unit
    ) {
        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            data
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onItemSelected(parent, view, position, id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

}