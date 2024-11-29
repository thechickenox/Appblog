package com.example.appblog.presentation.auth

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.appblog.core.Result
import com.example.appblog.domain.auth.AuthRepo
import kotlinx.coroutines.Dispatchers
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(private val repo: AuthRepo) : ViewModel() {

    // Para signIn, esperamos un resultado de tipo FirebaseUser
    fun signIn(email: String, password: String) = liveData<Result<FirebaseUser>>(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            val user = repo.signIn(email, password) // Llamada al repositorio
            if (user != null) {
                emit(Result.Success(user)) // Emitir el resultado exitoso
            } else {
                emit(Result.Failure(Exception("Sign-in failed: User not found.")))
            }
        } catch (e: Exception) {
            emit(Result.Failure(e)) // En caso de error
        }
    }

    // Para signUp, esperamos un resultado de tipo FirebaseUser
    fun signUp(email: String, password: String, username: String) = liveData<Result<FirebaseUser>>(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            val user = repo.signUp(email, password, username) // Llamada al repositorio
            if (user != null) {
                emit(Result.Success(user)) // Emitir el resultado exitoso
            } else {
                emit(Result.Failure(Exception("Sign-up failed: User not created.")))
            }
        } catch (e: Exception) {
            emit(Result.Failure(e)) // En caso de error
        }
    }

    // Para updateUserProfile, esperamos un resultado de tipo Boolean (indica éxito)
    fun updateUserProfile(imageBitmap: Bitmap, username: String) = liveData<Result<Boolean>>(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            val updateSuccess = repo.updateProfile(imageBitmap, username) // Llamada al repositorio
            emit(Result.Success(updateSuccess)) // Emitir resultado exitoso (asumimos que updateProfile devuelve un Boolean)
        } catch (e: Exception) {
            emit(Result.Failure(e)) // En caso de error
        }
    }
}

class AuthViewModelFactory(private val repo: AuthRepo) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
