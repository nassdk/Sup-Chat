package com.example.firabasefirstexperience.kotlin.model

class Chat {

    var receiver: String? = null
    var sender: String? = null
    var message: String? = null

    constructor(receiver: String, sender: String, message: String) {
        this.receiver = receiver
        this.sender = sender
        this.message = message
    }

    constructor() {

    }
}