package com.example.campusequipmentrental.ui.complete

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.campusequipmentrental.R
import com.example.campusequipmentrental.data.EquipmentRepository
import com.example.campusequipmentrental.databinding.FragmentCompleteBinding
import com.example.campusequipmentrental.util.NavKeys

class CompleteFragment : Fragment(R.layout.fragment_complete) {

    private var _binding: FragmentCompleteBinding? = null
    private val binding: FragmentCompleteBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCompleteBinding.bind(view)

        val args = arguments
        val equipment = EquipmentRepository.findById(
            args?.getInt(NavKeys.EQUIPMENT_ID, -1) ?: -1
        )
        val applicant = args?.getString(NavKeys.APPLICANT_NAME).orEmpty()
        val studentId = args?.getString(NavKeys.STUDENT_ID).orEmpty()
        val purpose = args?.getString(NavKeys.PURPOSE).orEmpty()
        val days = args?.getInt(NavKeys.RENTAL_DAYS, 1) ?: 1

        binding.tvCompleteEquipment.text = if (equipment != null) {
            getString(R.string.complete_equipment_format, equipment.icon, equipment.name)
        } else {
            getString(R.string.unknown_equipment)
        }
        binding.tvCompleteApplicant.text = getString(R.string.complete_applicant_format, applicant)
        binding.tvCompleteStudentId.text = getString(R.string.complete_student_id_format, studentId)
        binding.tvCompletePeriod.text = getString(R.string.complete_period_format, days)
        binding.tvCompletePurpose.text = getString(R.string.complete_purpose_format, purpose)

        // 목록 화면까지 되돌아가기 (그 사이 화면은 백스택에서 제거)
        binding.btnGoList.setOnClickListener {
            findNavController().popBackStack(R.id.equipmentListFragment, false)
        }
        // 처음 화면까지 되돌아가기
        binding.btnGoHome.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
