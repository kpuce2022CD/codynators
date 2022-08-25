package com.example.data.repository

interface AuthRepository {
    fun signIn(email: String, password: String): Boolean
    fun signUp(email: String, password: String, name: String): Boolean
    fun logOut(result: () -> Unit)
}