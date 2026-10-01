package com.example.campusequipmentrental.util

import androidx.annotation.DrawableRes
import com.example.campusequipmentrental.R
import com.example.campusequipmentrental.model.RentalStatus

/** 대여 상태에 맞는 배지 배경 drawable. 목록/상세 화면에서 같이 사용합니다. */
@DrawableRes
fun RentalStatus.badgeBackground(): Int = when (this) {
    RentalStatus.AVAILABLE -> R.drawable.bg_status_available
    RentalStatus.RENTED -> R.drawable.bg_status_unavailable
    RentalStatus.MAINTENANCE -> R.drawable.bg_status_maintenance
}
