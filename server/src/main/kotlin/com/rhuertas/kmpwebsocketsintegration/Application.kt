package com.rhuertas.kmpwebsocketsintegration

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(WebSockets)

    routing {
        get("/") {
            call.respondText(sayHello("Ktor"))
        }

        webSocket("/ws") {
            send("Connected")

            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val message = frame.readText()

                if (message.equals("bye", ignoreCase = true)) {
                    close(CloseReason(CloseReason.Codes.NORMAL, "Client closed"))
                } else {
                    send("Echo: $message")
                }
            }
        }
    }
}