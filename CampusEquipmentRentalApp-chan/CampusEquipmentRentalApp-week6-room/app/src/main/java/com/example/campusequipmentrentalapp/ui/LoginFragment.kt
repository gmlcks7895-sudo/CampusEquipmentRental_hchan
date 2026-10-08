package com.example.campusequipmentrentalapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.campusequipmentrentalapp.R
import com.example.campusequipmentrentalapp.data.AppDatabase
import com.example.campusequipmentrentalapp.data.UserRepository
import com.example.campusequipmentrentalapp.data.UserSession
import com.example.campusequipmentrentalapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)

        // Room DB 연결
        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        val userRepository = UserRepository(userDao)

        // 처음 실행하면 실습용 계정 넣기
        viewLifecycleOwner.lifecycleScope.launch {
            userRepository.insertSampleUsers()
        }

        binding.btnLogin.setOnClickListener {
            val loginId = binding.etLoginId.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // 빈 값 검사
            if (loginId.isEmpty()) {
                binding.etLoginId.error = "사용자 ID를 입력하세요."
                binding.etLoginId.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "비밀번호를 입력하세요."
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            // DB에서 사용자 검색 (코루틴 안에서 실행)
            viewLifecycleOwner.lifecycleScope.launch {
                val user = userRepository.login(loginId, password)

                if (user == null) {
                    Toast.makeText(
                        requireContext(),
                        "ID 또는 비밀번호가 올바르지 않습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }

                // 로그인 성공
                UserSession.login(user)

                Toast.makeText(
                    requireContext(),
                    "${user.name}님 로그인 성공",
                    Toast.LENGTH_SHORT
                ).show()

                findNavController().navigate(
                    R.id.action_loginFragment_to_homeFragment
                )
            }
        }

        // 회원가입 화면으로 이동
        binding.btnGoSignup.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_signupFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
