package com.rhuertas.kmpwebsocketsintegration

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return sayHello(platform.name)
    }
}