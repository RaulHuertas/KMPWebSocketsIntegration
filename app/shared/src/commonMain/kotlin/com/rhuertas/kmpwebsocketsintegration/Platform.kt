package com.rhuertas.kmpwebsocketsintegration

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform