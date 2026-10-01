package com.example.campusequipmentrental.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.campusequipmentrental.R
import com.example.campusequipmentrental.data.EquipmentRepository
import com.example.campusequipmentrental.databinding.FragmentEquipmentDetailBinding
import com.example.campusequipmentrental.model.Equipment
import com.example.campusequipmentrental.util.NavKeys
import com.example.campusequipmentrental.util.badgeBackground

class EquipmentDetailFragment : Fragment(R.layout.fragment_equipment_detail) {

    private var _binding: FragmentEquipmentDetailBinding? = null
    private val binding: FragmentEquipmentDetailBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEquipmentDetailBinding.bind(view)

        binding.btnBackDetail.setOnClickListener {
            findNavController().popBackStack()
        }

        val equipmentId = arguments?.getInt(NavKeys.EQUIPMENT_ID, -1) ?: -1
        val equipment = EquipmentRepository.findById(equipmentId)

        if (equipment == null) {
            showNotFound()
        } else {
            showEquipment(equipment)
        }
    }

    private fun showEquipment(equipment: Equipment) {
        binding.tvDetailIcon.text = equipment.icon
        binding.tvDetailName.text = equipment.name
        binding.tvDetailCategory.text = equipment.category
        binding.tvDetailStatus.text = equipment.status.label
        binding.tvDetailStatus.setBackgroundResource(equipment.status.badgeBackground())
        binding.tvDetailLocation.text = getString(R.string.location_format, equipment.location)
        binding.tvDetailMaxDays.text = getString(R.string.max_days_format, equipment.maxRentalDays)
        binding.tvDetailDescription.text = equipment.description

        if (equipment.status.isAvailable) {
            binding.btnApplyRental.isEnabled = true
            binding.btnApplyRental.setText(R.string.apply_rental)
            binding.btnApplyRental.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt(NavKeys.EQUIPMENT_ID, equipment.id)
                }
                findNavController().navigate(
                    R.id.action_equipmentDetailFragment_to_rentalFragment,
                    bundle
                )
            }
        } else {
            // 대여 중 / 점검 중이면 신청 불가
            binding.btnApplyRental.isEnabled = false
            binding.btnApplyRental.setText(R.string.currently_unavailable)
        }
    }

    private fun showNotFound() {
        binding.tvDetailName.setText(R.string.unknown_equipment)
        binding.tvDetailDescription.setText(R.string.equipment_not_found)
        binding.tvDetailStatus.visibility = View.GONE
        binding.btnApplyRental.isEnabled = false
        binding.btnApplyRental.setText(R.string.currently_unavailable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
