package com.example.campusequipmentrental.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.campusequipmentrental.R
import com.example.campusequipmentrental.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bind는 이미 만들어진 뷰 객체를 뷰바인딩 객체에 연결
        //inflate는 뷰 객체를 생성하고 뷰바인딩 객체에 연결
        _binding = FragmentHomeBinding.bind(view)
        binding.btnViewEquipment.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_equipmentListFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}