package com.example.campusequipmentrentalapp.model

data class Equipment (
    val id: Int,
    val name: String,
    val category: String,
    val icon: String,
    val status: RentalStatus,
    val maxRentalDays: Int,
    val location: String,
    val description: String
)

enum class RentalStatus(
    val label: String,
    val isAvailable: Boolean
) {
    AVAILABLE("대여 가능", true),
    RENTED("대여 중", false),
    MAINTENANCE("점검 중", false)
}

