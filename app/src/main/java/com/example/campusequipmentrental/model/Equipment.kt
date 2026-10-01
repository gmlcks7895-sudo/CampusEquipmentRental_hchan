package com.example.campusequipmentrental.model
/*
RecyclerView 한 행에 표시할 기자재 데이터
 */
data class Equipment (
    var id: Int,
    var name: String,
    var category: String,
    var icon: String,
    var status: RentalStatus,
    var maxRentalDays: Int,
    var location: String,
    var description: String
)

//enum class: 상수들의 집합을 정의함. RentalStatus.RENTED하면 label이 대여 중, isAvailable이 false가 됨.
enum class RentalStatus(
    val label: String,
    val isAvailable: Boolean
){
    AVAILABLE("대여 가능", true),
    RENTED("대여 중", false),
    MAINTENANCE("점검 중", false)
}