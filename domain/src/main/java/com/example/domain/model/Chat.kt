package com.example.domain.model

class Chat {

    var receiver: String = ""
    var sender: String = ""
    var message: String = ""

    constructor(receiver: String, sender: String, message: String) {
        this.receiver = receiver
        this.sender = sender
        this.message = message
    }

    constructor() {

    }
}