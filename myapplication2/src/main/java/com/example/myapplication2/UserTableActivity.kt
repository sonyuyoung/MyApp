package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication2.SQLlight.DatabaseHelper
import com.example.myapplication2.adapter.MultiImageAdapter2
import com.example.myapplication2.databinding.ActivityUserTableBinding
import com.example.myapplication2.model.User

class UserTableActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserTableBinding

    //조회시 사용했던, 전역 변수들
    // 회원가입된 회원들을 담을 리스트.
    var list = ArrayList<User>()
    // 출력을 리사이클러뷰를 사용해서, 준비물) 1)어댑터 2) 뷰홀더 3) 목록요소의 뷰가
    var adapter = MultiImageAdapter2(list,this)
    // 특전 버튼, 디비 접근 인스턴스, 전역.
    var getUserBtn: Button? = null
    var myDB: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserTableBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserBtn = binding.getUserBtn
        myDB = DatabaseHelper(this)

        //리사이클러뷰 붙이기
        val layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.layoutManager = layoutManager
        binding.userRecyclerView.adapter = adapter


        binding.getUserBtn.setOnClickListener {
            viewAll()
        }

        binding.joinUserBtn.setOnClickListener {
            val intent = Intent(this@UserTableActivity,JoinActivity2::class.java)
            startActivity(intent)
            finish()
        }



    }

    // 출력 됨. 기존 방법1
//    fun viewAll() {
//        getUserBtn!!.setOnClickListener(View.OnClickListener {
//            // res에 조회된 , 테이블의 내용이 들어가 있다. select 의 조회의 결괏값있다.
//            // res -> Cursor
//            val res = myDB!!.allData
//            // 결과가 없을 때
//            if (res.count == 0) {
//                ShowMessage("실패", "데이터를 찾을 수 없습니다.")
//                return@OnClickListener
//            }
//            val buffer = StringBuffer()
//            while (res.moveToNext()) {
//                buffer.append(
//                    //코틀린 3중 따옴표, 멀티 라인.
//                    // 1행의 첫번째 컬럼을 가져오기.
//                    """
//       ID: ${res.getString(0)}
//
//    """.trimIndent()
//                )
//                buffer.append(
//                    """
//    이메일: ${res.getString(1)}
//
//    """.trimIndent()
//                )
//                buffer.append(
//                    """
//    이름: ${res.getString(2)}
//
//    """.trimIndent()
//                )
//
//                buffer.append(
//                    """
//    비밀번호: ${res.getString(3)}
//
//    """.trimIndent()
//                )
//
//                buffer.append(
//                    """
//    전화번호: ${res.getString(4)}
//
//    """.trimIndent()
//                )
//                buffer.append(
//                    """
//    주소: ${res.getString(5)}
//
//
//    """.trimIndent()
//                )
//            }
//
//            ShowMessage("회원목록", buffer.toString())
//        })
//    }


    // onCreate
//     방법2
    fun viewAll() {
        getUserBtn?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//        getUserBtn!!.setOnClickListener(View.OnClickListener {
            // res에 조회된 , 테이블의 내용이 들어가 있다. select 의 조회의 결괏값있다.
            // res -> Cursor = 테이블
                val res = myDB?.allData
                // 결과가 없을 때
                if (res?.count == 0) {
                    ShowMessage("실패", "데이터를 찾을 수 없습니다.")
                    return
                }

                list.clear()

                if (res != null) {
                    while (res.moveToNext()) {
                        var user: User? = null

                        val ID = res.getString(0) ?: ""
                        val EMAIL = res.getString(1) ?: ""
                        val NAME = res.getString(2) ?: ""
                        val PASSWORD = res.getString(3) ?: ""
                        val PHONE = res.getString(4) ?: ""
                        val ADDRESS = res.getString(5) ?: ""
                        val PROFILEURI = res.getString(6) ?: ""
//                        val GENDER = res.getString(7) ?: ""
                        user = User(ID, EMAIL, NAME, PASSWORD, PHONE, ADDRESS,PROFILEURI)
                        list.add(user)
                    }
                }
                adapter.notifyDataSetChanged()
            }
        })
    }

//    fun viewAll() {
//        // SAM (Single Abstrac Method) 함수형 인터페이스, 추상 메서드가 하나인 메서드.
//        // 람다식으로 표현 할 때 , 자주 이용되는 기법 중 하나임.
//        getUserBtn!!.setOnClickListener(View.OnClickListener {
//            // res에 조회된 , 테이블의 내용이 들어가 있다. select 의 조회의 결괏값있다.
//            // res -> Cursor = 테이블
//            val res = myDB!!.allData
//            // 결과가 없을 때
//            if (res.count == 0) {
//                ShowMessage("실패", "데이터를 찾을 수 없습니다.")
//                return@OnClickListener
//            }
//
//            list.clear()
//
//            while (res.moveToNext()) {
//                var user : User? = null
//
//                val ID = res.getString(0)
//                val EMAIL = res.getString(1)
//                val NAME = res.getString(2)
//                val PASSWORD = res.getString(3)
//                val PHONE = res.getString(4)
//                val ADDRESS = res.getString(5)
//                val PROFILEURI = res.getString(6)
//                user = User(ID,EMAIL,NAME,PASSWORD,PHONE,ADDRESS,PROFILEURI)
//                list.add(user)
//            }
//            adapter.notifyDataSetChanged()
//        })
//    }

    fun ShowMessage(title: String?, Message: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }
}