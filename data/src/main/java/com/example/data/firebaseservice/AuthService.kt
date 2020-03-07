package com.example.data.firebaseservice

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthService {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun userLogIn(login: String, pass: String): Task<AuthResult?> {
        return auth.signInWithEmailAndPassword(login, pass)
    }

    fun userRegistration(email: String, pass: String): Task<AuthResult?> {
        return auth.createUserWithEmailAndPassword(email, pass)
    }

    fun restorePass(email: String): Task<Void?> {
        return auth.sendPasswordResetEmail(email)
    }
}