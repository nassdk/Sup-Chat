package com.example.data.firebaseservice

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthService {

    fun userAuthCase(email: String, password: String): Task<AuthResult?> {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun userRegistrationCase(textEmail: String, textPassword: String): Task<AuthResult?> {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        return auth.createUserWithEmailAndPassword(textEmail, textPassword)
    }

    fun restorePassCase(email: String): Task<Void?> {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        return auth.sendPasswordResetEmail(email)
    }
}