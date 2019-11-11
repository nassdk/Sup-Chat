package com.nassdk.supchat.domain.model

class User {

    var id: String? = null
    var userName: String? = null
    var password: String? = null
    private var eMail: String? = null
    var imageURL: String? = null
    var status: String? = null

    constructor(id: String, userName: String, password: String, eMail: String, imageURL: String, status: String) {

        this.id = id
        this.userName = userName
        this.password = password
        this.eMail = eMail
        this.imageURL = imageURL
        this.status = status
    }

    constructor() {

    }

    fun geteMail(): String? {
        return eMail
    }

    fun seteMail(eMail: String) {
        this.eMail = eMail
    }
}