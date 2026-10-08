package com.example.campusequipmentrentalapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.campusequipmentrentalapp.R
import com.example.campusequipmentrentalapp.data.EquipmentRepository
import com.example.campusequipmentrentalapp.databinding.FragmentEquipmentDetailBinding
import com.example.campusequipmentrentalapp.model.RentalStatus
import com.example.campusequipmentrentalapp.util.NavKeys

class EquipmentDetailFragment : Fragment(R.layout.fragment_equipment_detail) {

    private var _binding: FragmentEquipmentDetailBinding? = null
    private val binding: FragmentEquipmentDetailBinding
        get() = _binding!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentEquipmentDetailBinding.bind(view)

        val equipmentId = arguments?.getInt(NavKeys.EQUIPMENT_ID, -1) ?: -1
        val equipment = EquipmentRepository.findById(equipmentId)

        if (equipment == null) {
            Toast.makeText(
                requireContext(),
                getString(R.string.equipment_not_found),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
            return
        }

        binding.tvDetailIcon.text = equipment.icon
        binding.tvDetailName.text = equipment.name
        binding.tvDetailCategory.text = equipment.category
        binding.tvDetailStatus.text = equipment.status.label
        binding.tvDetailStatus.setBackgroundResource(
            when (equipment.status) {
                RentalStatus.AVAILABLE -> R.drawable.bg_status_available
                RentalStatus.RENTED -> R.drawable.bg_status_unavailable
                RentalStatus.MAINTENANCE -> R.drawable.bg_status_maintenance
            }
        )
        binding.tvDetailDescription.text = equipment.description
        binding.tvDetailLocation.text = getString(
            R.string.location_format,
            equipment.location
        )
        binding.tvDetailMaxDays.text = getString(
            R.string.max_days_format,
            equipment.maxRentalDays
        )

        binding.btnRentEquipment.isEnabled = equipment.status.isAvailable
        binding.btnRentEquipment.text = if (equipment.status.isAvailable) {
            getString(R.string.apply_rental)
        } else {
            getString(R.string.currently_unavailable)
        }

        binding.btnRentEquipment.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(NavKeys.EQUIPMENT_ID, equipment.id)
            }

            findNavController().navigate(
                R.id.action_equipmentDetailFragment_to_rentalFragment,
                bundle
            )
        }


        binding.btnBackDetail.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}