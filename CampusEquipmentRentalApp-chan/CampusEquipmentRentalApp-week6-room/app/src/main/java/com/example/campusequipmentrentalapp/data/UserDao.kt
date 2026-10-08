package com.example.campusequipmentrentalapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.campusequipmentrentalapp.model.User

@Dao
interface UserDao {

    // 회원가입
    @Insert
    suspend fun insert(user: User)

    // ID로 사용자 찾기 (중복 확인용)
    @Query("SELECT * FROM users WHERE loginId = :loginId LIMIT 1")
    suspend fun findByLoginId(loginId: String): User?

    // 로그인 (ID + 비밀번호가 모두 맞는 사용자)
    @Query("SELECT * FROM users WHERE loginId = :loginId AND password = :password LIMIT 1")
    suspend fun login(loginId: String, password: String): User?

    // 저장된 사용자 수 (샘플 데이터 넣을지 판단용)
    @Query("SELECT COUNT(*) FROM users")
    suspend fun count(): Int
}
