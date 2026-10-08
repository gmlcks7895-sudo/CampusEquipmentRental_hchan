package com.example.campusequipmentrentalapp.data

import com.example.campusequipmentrentalapp.model.User
import com.example.campusequipmentrentalapp.model.UserRole

class UserRepository(private val userDao: UserDao) {

    // 로그인: 맞는 사용자가 없으면 null
    suspend fun login(loginId: String, password: String): User? {
        return userDao.login(loginId, password)
    }

    // 회원가입: 이미 있는 ID면 false, 성공하면 true
    suspend fun register(user: User): Boolean {
        if (userDao.findByLoginId(user.loginId) != null) {
            return false
        }
        userDao.insert(user)
        return true
    }

    // DB가 비어 있을 때만 실습용 계정 추가 (비밀번호는 모두 1234)
    suspend fun insertSampleUsers() {
        if (userDao.count() > 0) return

        userDao.insert(User(loginId = "20260010", password = "1234", name = "김민수", department = "컴퓨터소프트웨어학과", role = UserRole.STUDENT))
        userDao.insert(User(loginId = "prof01", password = "1234", name = "박교수", department = "컴퓨터소프트웨어학과", role = UserRole.PROFESSOR))
        userDao.insert(User(loginId = "assist01", password = "1234", name = "이조교", department = "컴퓨터소프트웨어학과", role = UserRole.ASSISTANT))
    }
}
