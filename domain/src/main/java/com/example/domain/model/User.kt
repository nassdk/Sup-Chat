package com.example.domain.model

class User {

    var id: String = ""
    var userName: String = ""
    var password: String = ""
    private var eMail: String = ""
    var imageURL: String = "default"
    var status: String = "offline"

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