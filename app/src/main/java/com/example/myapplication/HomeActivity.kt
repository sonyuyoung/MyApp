package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.recycler.MyAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var toggle: ActionBarDrawerToggle

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 툴바 붙이기
        setSupportActionBar(binding.toolbar)
        //탭 레이아웃과 뷰페이저 2연동 !
        val tabLayout = binding.tabs2
        val viewPager = binding.viewPagerTab
//           인덱스 = 포지션
//        viewPager.adapter = ViewPagerAdapterTest(this)
//
//        TabLayoutMediator(tabLayout,viewPager){
//                tab,position -> tab.text = "Tab${position+1}"
//
//        }.attach()
        viewPager.adapter = ViewPagerAdapterTest(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // 여기서 탭의 이름을 변경할 수 있어
            when (position) {
                0 -> {
                    tab.text = "첫 번째 탭"
                    tab.setIcon(R.drawable.matzip) // 이미지 리소스를 지정해야 함
                }
                1 -> tab.text = "맛잘알리스트"
                2 -> tab.text = "랭킹"
                // 추가적으로 필요한 만큼 계속해서 설정 가능
                else -> tab.text = "기타 탭"
            }
        }.attach()


        // 더미로 사용한 텍스트 ..!  나중에는 데이터를 가지고와서 바인딩할것임
        var datas = mutableListOf<String>()
        for (i in 1..10) {
            datas.add(" 밥그릇 $i ")
        }
        //기본 값으로 세로방향 출력
        //binding.recyclerView.layoutManager = LinearLayoutManager(this)
        // 가로출력
        var layoutManager = LinearLayoutManager(this)
        // 담아두고
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        //recyclerView 해당 .. 수평으로 넣기
        binding.recyclerView.layoutManager = layoutManager
        //액티비티 - > 리사이클러 뷰 -> 실제데이터를 연결하는 부분
        binding.recyclerView.adapter = MyAdapter(datas)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

        //시스템에 있는 액션바에 업버튼 붙이기
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Using view binding to handle button click
//        binding.loginBtn.setOnClickListener {
//            val intent = Intent(this@MainActivity, LoginFormActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.joinBtn.setOnClickListener {
//            val intent = Intent(this@MainActivity, JoinActivity::class.java)
//            startActivity(intent)
//        }


        // 이벤트 핸들러 main_drawer_view it -> 아이템요소 ( 로그인, 로그아웃 , 메인가기등)
        binding.mainDrawerView.setNavigationItemSelectedListener { it ->
            if (it.title == "로그인") {
                Toast.makeText(this@HomeActivity, "로그인 화면 이동", Toast.LENGTH_SHORT)
                    .show()
            } else if (it.title == "로그아웃") {
                Toast.makeText(this@HomeActivity, "로그아웃 화면 이동", Toast.LENGTH_SHORT)
                    .show()
            } else if (it.title == "메인가기") {
                Toast.makeText(this@HomeActivity, "메인가기 화면 이동", Toast.LENGTH_SHORT)
                    .show()
            }
            true
        }


        // 드로워 화면에 액션 버튼 클릭시 -> 드로워 화면에 나오게.
        toggle = ActionBarDrawerToggle(
            this@HomeActivity,
            binding.drawer, R.string.open, R.string.close
        )
        // 화면에 붙이는 작업, 적용하기.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // 버튼 클릭시, 동기화, 드러워 화면을 열어주는 기능.
        toggle.syncState()

    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d("syy", "test")
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // 액션바에 오버플로우 메뉴 붙이기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        // 검색 뷰에, 이벤트 추가하기.
        val menuItem = menu?.findItem(R.id.menu_toolbar_search)
        // menuItem 의 형을 SearchView 타입으로 변환, 형변환
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                //검색어가 변경시 마다, 실행될 로직을 추가.
                Log.d("syy", "텍스트 변경시 마다 호출 : ${newText} ")
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색어가 제출이 되었을 경우, 연결할 로직.
                // 사용자 디비, 검색을하고, 그 결과 뷰를 출력하는 형태.
                Toast.makeText(this@HomeActivity, "검색어가 전송됨 : ${query}", Toast.LENGTH_SHORT).show()
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
//        } else if (R.id.menu_toolbar1 == item.itemId) {
//            Toast.makeText(this@HomeActivity, "툴바메뉴1 클릭됨", Toast.LENGTH_SHORT).show()
//            true
        }
        return super.onOptionsItemSelected(item)
    }
}
//}
//
//else if (R.id.menu_toolbar2 == item.itemId) {
//    Toast.makeText(this@HomeActivity, "툴바메뉴2 클릭됨", Toast.LENGTH_SHORT).show()
//    true
//} else if (R.id.menu_toolbar3 == item.itemId) {
//    Toast.makeText(this@HomeActivity, "툴바메뉴3 클릭됨", Toast.LENGTH_SHORT).show()
//    true
//}