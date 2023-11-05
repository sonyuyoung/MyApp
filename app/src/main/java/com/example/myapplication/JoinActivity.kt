package com.example.myapplication

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.myapplication.SQLlight.DatabaseHelper
import com.example.myapplication.databinding.ActivityJoinBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class JoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJoinBinding
    lateinit var filePath: String

    var myDB: DatabaseHelper? = null

    var userEmail: EditText? = null
    var userName: EditText? = null
    var userPwd: EditText? = null
    var userPhone: EditText? = null
    var userAddress: EditText? = null
    var joinInBtn: Button? = null
    var buttonView: Button? = null
    var editTextID: EditText? = null
    var buttonUpdate: Button? = null
    var buttonDelete: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        myDB = DatabaseHelper(this)


        userEmail = binding.userEmail
        userName = binding.userName
        userPwd = binding.userPwd
        userPhone = binding.userPhone
        userAddress = binding.userAddress
        joinInBtn = binding.joinInBtn
        editTextID = binding.editTextID
        buttonView = binding.buttonView
        buttonUpdate = binding.buttonUpdate
        buttonDelete = binding.buttonDelete


        AddData()
        viewAll()
        UpdateData()
        DeleteData()

        binding.radio1.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(this@JoinActivity, "남자 클릭됨", Toast.LENGTH_SHORT).show()
        }
        binding.radio2.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(this@JoinActivity, "여자 클릭됨", Toast.LENGTH_SHORT).show()
        }
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            try {
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.profile_img_with),
                    resources.getDimensionPixelSize(R.dimen.profile_img_height),
                )
                val options = BitmapFactory.Options()
                options.inSampleSize = calRatio

                // 사진을 읽은 바이트
                var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                inputStream!!.close()
                inputStream = null
                binding.resultUserImage.setImageBitmap(bitmap)

                Log.d("lsy", "갤러리에서 선택된 사진의 크기 비율 calRatio : $calRatio")

            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("syy", "사진 출력 실패")

            }

        }

        // 버튼클릭시 : 갤러리 앱 호출,
        binding.galleryBtn.setOnClickListener {
            //갤러리 앱 호출, 인텐트의 액션 문자열을 사용함. 시스템꺼를 사용해서 정해진 문자열.
            // Intent.ACTION_PICK : 갤러리 호출
            // MediaStore.Images.Media.EXTERNAL_CONTENT_URI : 외부 저장소(갤러리)의 사진의 위치
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // data type 지정.
            intent.type = "image/*"
            // 후처리 함수 호출 .-> 데이터 가져오기 위한 로직.
            // requestGalleryLauncher: 아직 후처리 함수 정의 안했음.
            requestGalleryLauncher.launch(intent)

        }

        var requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            // 원본사진 크기 조절하는 비율단위(정수값 )
            // 사진을  디바이스에 넣었을때 어느정도 비율이 줄어들었음
            val calRatio = calculateInSampleSize(
                Uri.fromFile(File(filePath)),
                resources.getDimensionPixelSize(R.dimen.profile_img_with),
                resources.getDimensionPixelSize(R.dimen.profile_img_height),
            )
            val options = BitmapFactory.Options()
            options.inSampleSize = calRatio
            // 촬영된 사진을 bitmap 타입으로 변환.
            val bitmap = BitmapFactory.decodeFile(filePath, options)
            // 비트맵 타입으로 변환된 사진을 출력하기 결과 뷰에
            binding.resultUserImage.setImageBitmap(bitmap)


        }
        binding.cameraBtn.setOnClickListener {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            var storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile(
                "JPEG_${timeStamp}_", ".jpg", storageDir
            )
            filePath = file.absolutePath
            Log.d("syy", "file.absolutePath:$filePath")

            val photoURI: Uri = FileProvider.getUriForFile(
                this@JoinActivity,
                "com.example.myapplication.fileprovider",
                file
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            requestCameraFileLauncher.launch(intent)
        }

    }

    // onCreate 끝
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        // 안드로이드에서 이미지타입을 비트맵이라고 정하는데 거기에 옵션을 넣는다
        val options = BitmapFactory.Options()
        //사진에 영향을 안주고 옵션을 제공
        options.inJustDecodeBounds = true
        //이미지로딩
        try {
            var inputStream = contentResolver.openInputStream(fileUri)
            // 변환작업
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("syy", "사진크기비율계산실패")
        }
        // 비율계산
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        // 1: 원본사이즈
        var inSampleSize = 1
        //조건문을 이용해서 내가 요구하는 (원본의 크기예 ) 높이보다 크거나 , 내가 요구하는 폭보다 크면
        //reqHeight/reqHeight:  원하는 사이즈
        if (height > reqHeight || width > reqWidth) {
            //반으로 접기
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            // 반으로 줄인사이즈를 inSampleSize 로 나누어서
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                // 비율을 2배씩 증가 , 값이 증가하면 사이즈의 크기는 줄어든다
                inSampleSize *= 2
            }
        }
        return inSampleSize


    }


    fun AddData() {
        joinInBtn!!.setOnClickListener {
            val isInserted = myDB!!.insertData(

                userEmail!!.text.toString(),
                userName!!.text.toString(),
                userPwd!!.text.toString(),
                userPhone!!.text.toString(),
                userAddress!!.text.toString()

            )
            if (isInserted == true)
                Toast.makeText(this@JoinActivity, "데이터추가 성공", Toast.LENGTH_LONG)
                    .show()
            else Toast.makeText(this@JoinActivity, "데이터추가 실패", Toast.LENGTH_LONG).show()
        }
    }


    // 데이터베이스 읽어오기
    fun viewAll() {
        buttonView!!.setOnClickListener(View.OnClickListener {
            // res에 조회된 , 테이블의 내용이 들어가 있다. select 의 조회의 결괏값있다.
            // res -> Cursor
            val res = myDB!!.allData
            // 결과가 없을 때
            if (res.count == 0) {
                ShowMessage("실패", "데이터를 찾을 수 없습니다.")
                return@OnClickListener
            }
            val buffer = StringBuffer()
            while (res.moveToNext()) {
                buffer.append(
                    //코틀린 3중 따옴표, 멀티 라인.
                    // 1행의 첫번째 컬럼을 가져오기.
                    """
       ID: ${res.getString(0)}

    """.trimIndent()
                )
                buffer.append(
                    """
    이메일: ${res.getString(1)}

    """.trimIndent()
                )
                buffer.append(
                    """
    이름: ${res.getString(2)}

    """.trimIndent()
                )

                buffer.append(
                    """
    비밀번호: ${res.getString(3)}

    """.trimIndent()
                )

                buffer.append(
                    """
    전화번호: ${res.getString(4)}

    """.trimIndent()
                )
                buffer.append(
                    """
    주소: ${res.getString(5)}


    """.trimIndent()
                )
            }
            ShowMessage("회원목록", buffer.toString())
        })
    }

    //    데이터베이스 수정하기
    fun UpdateData() {
        buttonUpdate!!.setOnClickListener {
            val isUpdated = myDB!!.updateData(
                editTextID!!.text.toString(),
                userEmail!!.text.toString(),
                userName!!.text.toString(),
                userPwd!!.text.toString(),
                userPhone!!.text.toString(),
                userAddress!!.text.toString()
            )
            if (isUpdated)
                Toast.makeText(this@JoinActivity, "데이터 수정 성공", Toast.LENGTH_LONG).show()
            else Toast.makeText(this@JoinActivity, "데이터 수정 실패", Toast.LENGTH_LONG).show()
        }
    }

    // 데이터베이스 삭제하기
    fun DeleteData() {
        buttonDelete!!.setOnClickListener {
            val deleteRows = myDB!!.deleteData(editTextID!!.text.toString())
            if (deleteRows > 0)
                Toast.makeText(this@JoinActivity, "데이터 삭제 성공", Toast.LENGTH_LONG)
                    .show()
            else Toast.makeText(this@JoinActivity, "데이터 삭제 실패", Toast.LENGTH_LONG)
                .show()
        }
    }

    fun ShowMessage(title: String?, Message: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }


}