package com.example.campusequipmentrental.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.campusequipmentrental.R
import com.example.campusequipmentrental.adapter.EquipmentAdapter
import com.example.campusequipmentrental.data.EquipmentRepository
import com.example.campusequipmentrental.databinding.FragmentEquipmentListBinding
import com.example.campusequipmentrental.util.NavKeys

class EquipmentListFragment : Fragment(R.layout.fragment_equipment_list) {

    private var _binding: FragmentEquipmentListBinding? = null
    private val binding: FragmentEquipmentListBinding
        get() = _binding!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentEquipmentListBinding.bind(view)

        val equipmentAdapter = EquipmentAdapter(
            items = EquipmentRepository.equipmentList,
            onItemClick = { equipment ->
                val bundle = Bundle().apply {
                    putInt(NavKeys.EQUIPMENT_ID, equipment.id)
                }

                findNavController().navigate(
                    R.id.action_equipmentListFragment_to_equipmentDetailFragment,
                    bundle
                )
            }
        )

        binding.recyclerEquipment.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = equipmentAdapter
            setHasFixedSize(true)
        }

        binding.btnBackList.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        binding.recyclerEquipment.adapter = null
        super.onDestroyView()
        _binding = null
    }
}