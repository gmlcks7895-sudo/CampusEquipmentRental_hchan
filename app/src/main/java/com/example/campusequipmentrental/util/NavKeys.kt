package com.example.campusequipmentrental.util

/**
 * Navigation으로 화면 간 데이터를 전달할 때 사용하는 Bundle 키 모음.
 * nav_graph.xml의 <argument android:name="..."> 값과 반드시 같아야 합니다.
 */
object NavKeys {
    const val EQUIPMENT_ID = "equipmentId"
    const val APPLICANT_NAME = "applicantName"
    const val STUDENT_ID = "studentId"
    const val PURPOSE = "purpose"
    const val RENTAL_DAYS = "rentalDays"
}
