package com.example.campusequipmentrentalapp.ui.signup

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.campusequipmentrentalapp.R
import com.example.campusequipmentrentalapp.data.AppDatabase
import com.example.campusequipmentrentalapp.data.UserRepository
import com.example.campusequipmentrentalapp.databinding.FragmentSignupBinding
import com.example.campusequipmentrentalapp.model.User
import com.example.campusequipmentrentalapp.model.UserRole
import kotlinx.coroutines.launch

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private var _binding: FragmentSignupBinding? = null
    private val binding: FragmentSignupBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSignupBinding.bind(view)

        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        val userRepository = UserRepository(userDao)

        // 역할 선택 Spinner (학생 / 교수 / 조교)
        val roles = UserRole.values()
        binding.spinnerRole.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            roles.map { it.label }
        )

        binding.btnSignup.setOnClickListener {
            val loginId = binding.etSignupId.text.toString().trim()
            val password = binding.etSignupPassword.text.toString().trim()
            val passwordCheck = binding.etSignupPasswordCheck.text.toString().trim()
            val name = binding.etSignupName.text.toString().trim()
            val department = binding.etSignupDepartment.text.toString().trim()
            val role = roles[binding.spinnerRole.selectedItemPosition]

            // 입력값 검사
            if (loginId.isEmpty()) {
                binding.etSignupId.error = "사용자 ID를 입력하세요."
                binding.etSignupId.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 4) {
                binding.etSignupPassword.error = "비밀번호는 4자 이상 입력하세요."
                binding.etSignupPassword.requestFocus()
                return@setOnClickListener
            }

            if (password != passwordCheck) {
                binding.etSignupPasswordCheck.error = "비밀번호가 일치하지 않습니다."
                binding.etSignupPasswordCheck.requestFocus()
                return@setOnClickListener
            }

            if (name.isEmpty()) {
                binding.etSignupName.error = "이름을 입력하세요."
                binding.etSignupName.requestFocus()
                return@setOnClickListener
            }

            if (department.isEmpty()) {
                binding.etSignupDepartment.error = "학과를 입력하세요."
                binding.etSignupDepartment.requestFocus()
                return@setOnClickListener
            }

            // DB에 저장
            viewLifecycleOwner.lifecycleScope.launch {
                val user = User(
                    loginId = loginId,
                    password = password,
                    name = name,
                    department = department,
                    role = role
                )

                val success = userRepository.register(user)

                if (!success) {
                    binding.etSignupId.error = "이미 사용 중인 ID입니다."
                    binding.etSignupId.requestFocus()
                    return@launch
                }

                Toast.makeText(
                    requireContext(),
                    "회원가입이 완료되었습니다. 로그인해 주세요.",
                    Toast.LENGTH_SHORT
                ).show()

                // 로그인 화면으로 돌아가기
                findNavController().popBackStack()
            }
        }

        binding.btnBackSignup.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
