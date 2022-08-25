package com.example.features.intro.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.App
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val fbAuth: FirebaseAuth,
    private val fbStore: FirebaseFirestore
) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isSuccess = MutableSharedFlow<Boolean>()
    val isSuccess = _isSuccess.asSharedFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            fbAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    success(true)
                    App.prefs.loginCheck = true
                }
                .addOnFailureListener {
                    success(false)
                }
        }
    }

    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val uid = it.user?.uid.toString()
                    val data = hashMapOf(
                        "name" to name,
                        "uid" to uid
                    )
                    fbStore.collection("User").document(uid)
                        .set(data)
                        .addOnSuccessListener {
                            success(true)
                        }.addOnFailureListener {
                            success(false)
                        }
                }.addOnFailureListener {
                    success(false)
                }
        }
    }

    private fun success(state: Boolean) {
        viewModelScope.launch {
            if (state) {
                _isLoading.value = false
                _isSuccess.emit(state)
            } else {
                _isLoading.value = false
                _isSuccess.emit(state)
            }
        }
    }
}