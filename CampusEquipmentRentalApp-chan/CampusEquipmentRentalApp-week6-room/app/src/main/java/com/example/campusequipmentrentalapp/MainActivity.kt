package com.example.campusequipmentrentalapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.campusequipmentrentalapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 코드 추가 수정 --by seoyk 2026.09.29
        /*
        * 1.상태바 높이 측정: 기기마다 다른 상단 상태바 높이(예: 48dp 등)를 정확히 측정합니다.
        * 2.안전 영역(Safe Area) 확보: 앱 최상위 뷰의 top 패딩으로 상태바 높이만큼 여백을
        * 자동으로 주어, UI 요소들을 상태바 아래의 터치 가능한 안전한 영역으로 밀어 내려줍니다.
        * UI가 시스템 바를 침범하지 않고, 사용자의 손가락 터치가 버튼 뷰로 정확하게
        * 입력되어 클릭 이벤트가 작동
        * */
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) {
            v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}