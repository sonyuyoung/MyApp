package com.example.myapplication.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemRecyclerBinding

// 뷰박스 : 목록 요소의 뷰 (?) 이미지 , 텍스트 잇는 레이아웃 인자값에 바인딩 ,그리고 상속
//(val binding: ItemRecyclerBinding)  주생성자
// : RecyclerView.ViewHolder(binding.root) 상속
class MyViewHolder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root)


// 뷰와 데이터 연결
// 뷰 생성해주는 역할 뷰 페이저 에서도 같은 패턴으로 사용할 예정
// 주생성자 (val datas : MutableList<String>) datas 더미 데이터
class MyAdapter(val datas : MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//리사이클ㄹ러 뷰의 어댑터를 상속받으면, 필수적으로 ... 재정의해야하는 함수들

    //뷰를 생성 해주는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        // 화면에 인스턴스화시킴 , 객체를 찍어낸다 ?
        MyViewHolder(
            ItemRecyclerBinding.inflate(
            // 액티비티위에 리사이클러 뷰를 넣으니까 액티비티가 부모요소 , 부모에 안붙이고 커스텀해서 붙이니까false
            LayoutInflater.from(parent.context),parent,false))




    // 출력할 목록 아이템의 크기 (사이즈), 더미 데이터를 사용할 예정.
    // datas 에서 무조곤 컬렉션( 리스트 ) 사용 하기 떄문에 사이즈를 카운트
    override fun getItemCount(): Int {
        Log.d("syy","getItemCount : ${datas.size}")
        return datas.size
    }

    // 해당뷰에 데이터를 연동(바인딩) 해주는 작업
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("syy", "onBindViewHolder : $position")
        //위에서 생성해둔 아이템 리사이클러 요소의 뷰를 말을하고 , 뷰홀더라는 부모요소를 상속받았기때문에 다형성에 의해 형변환을 해도 상관없음 .
        var binding = (holder as MyViewHolder).binding
        // 뷰데이터 출력
        binding.testText.text = datas[position]
        binding.itemRoot.setOnClickListener {
//            Toast.makeText(this@MyAdapter,)
            Log.d("syy", "item clicked:$position")
        }
    }

}
// 목록의 아이템의 요소 뷰를 만들기
