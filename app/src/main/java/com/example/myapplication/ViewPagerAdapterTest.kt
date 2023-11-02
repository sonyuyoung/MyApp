package com.example.myapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.fragment.Test1Fragment
import com.example.myapplication.fragment.Test2Fragment
import com.example.myapplication.fragment.Test3Fragment

class ViewPagerAdapterTest ( fragmentActivity : FragmentActivity): FragmentStateAdapter(fragmentActivity){
    //선언
    var testFragment : List<Fragment>

    //초기화
    init{
        testFragment = listOf(Test1Fragment(), Test2Fragment(), Test3Fragment())
    }
    override fun getItemCount(): Int = testFragment.size

    //position 이 바뀜
    override fun createFragment(position: Int): Fragment {
        val returnFrament : Fragment = testFragment[position]
        return  returnFrament
    }

}