package com.example.data.repositoryimpl

import com.example.data.firebaseservice.AuthService
import com.example.domain.repository.AuthRepository
import io.reactivex.Observable

class AuthRepositoryImpl(
        private val service: AuthService
) : AuthRepository {

    override fun userAuthCase(email: String, password: String): Observable<Unit> {}

    override fun userRegistrationCase(): Observable<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun restorePassCase(): Observable<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}