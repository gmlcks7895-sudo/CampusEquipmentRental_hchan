package com.example.campusequipmentrentalapp.data

import com.example.campusequipmentrentalapp.model.*;

object  UserSession {

    var currentUser : User? = null
        private set

    /** 로그린 **/
    fun login(user: User) {

        currentUser = user
    }

    /** 로그아웃 **/
    fun logout() {

        currentUser = null
    }

    /** 로그인 상태 확인 **/
   //   val isLoggedIn1: Boolean
   //     get() = currentUser != null


    fun isLoggedIn(): Boolean {
        return currentUser != null
    }



}