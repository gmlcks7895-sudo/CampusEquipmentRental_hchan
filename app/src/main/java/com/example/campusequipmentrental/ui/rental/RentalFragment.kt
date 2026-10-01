package com.example.campusequipmentrental.ui.rental

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.campusequipmentrental.R
import com.example.campusequipmentrental.data.EquipmentRepository
import com.example.campusequipmentrental.databinding.FragmentRentalBinding
import com.example.campusequipmentrental.model.Equipment
import com.example.campusequipmentrental.util.NavKeys

class RentalFragment : Fragment(R.layout.fragment_rental) {

    private var _binding: FragmentRentalBinding? = null
    private val binding: FragmentRentalBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRentalBinding.bind(view)

        binding.btnBackRental.setOnClickListener {
            findNavController().popBackStack()
        }

        val equipmentId = arguments?.getInt(NavKeys.EQUIPMENT_ID, -1) ?: -1
        val equipment = EquipmentRepository.findById(equipmentId)

        if (equipment == null || !equipment.status.isAvailable) {
            // 잘못된 접근(없는 기자재, 대여 불가 상태)이면 신청 못 하게 막기
            binding.tvSelectedEquipment.setText(R.string.equipment_not_found)
            binding.btnSubmitRental.isEnabled = false
            return
        }

        setupForm(equipment)
    }

    private fun setupForm(equipment: Equipment) {
        binding.tvSelectedEquipment.text = getString(
            R.string.selected_equipment_format,
            equipment.icon,
            equipment.name
        )
        binding.tvRentalRule.text = getString(
            R.string.rental_rule_format,
            equipment.maxRentalDays
        )

        // 1일 ~ maxRentalDays일까지만 선택 가능
        val periodOptions = (1..equipment.maxRentalDays).map { days ->
            getString(R.string.day_format, days)
        }
        val periodAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            periodOptions
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.spinnerPeriod.adapter = periodAdapter

        binding.btnSubmitRental.setOnClickListener {
            submit(equipment)
        }
    }

    private fun submit(equipment: Equipment) {
        val name = binding.etApplicantName.text?.toString().orEmpty().trim()
        val studentId = binding.etStudentId.text?.toString().orEmpty().trim()
        val purpose = binding.etPurpose.text?.toString().orEmpty().trim()
        val days = binding.spinnerPeriod.selectedItemPosition + 1

        // 입력 검증: 비어 있는 항목에 에러 표시
        binding.etApplicantName.error =
            if (name.isEmpty()) getString(R.string.enter_name) else null
        binding.etStudentId.error =
            if (studentId.isEmpty()) getString(R.string.enter_student_id) else null
        binding.etPurpose.error =
            if (purpose.isEmpty()) getString(R.string.enter_purpose) else null

        if (name.isEmpty() || studentId.isEmpty() || purpose.isEmpty()) return

        val bundle = Bundle().apply {
            putInt(NavKeys.EQUIPMENT_ID, equipment.id)
            putString(NavKeys.APPLICANT_NAME, name)
            putString(NavKeys.STUDENT_ID, studentId)
            putString(NavKeys.PURPOSE, purpose)
            putInt(NavKeys.RENTAL_DAYS, days)
        }
        findNavController().navigate(
            R.id.action_rentalFragment_to_completeFragment,
            bundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
