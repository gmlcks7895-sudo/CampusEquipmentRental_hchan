package com.example.campusequipmentrentalapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Room 테이블: users
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val loginId: String,
    val password: String,
    val name: String,
    val department: String,
    val role: UserRole
)

enum class UserRole(val label: String) {
    STUDENT("학생"),
    PROFESSOR("교수"),
    ASSISTANT("조교")
}
