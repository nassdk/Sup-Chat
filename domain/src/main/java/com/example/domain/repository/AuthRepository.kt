package com.example.domain.repository

import io.reactivex.Observable

interface AuthRepository {

    fun userAuthCase(email: String, password: String): Observable<Unit>
    fun userRegistrationCase(): Observable<Unit>
    fun restorePassCase(): Observable<Unit>
}