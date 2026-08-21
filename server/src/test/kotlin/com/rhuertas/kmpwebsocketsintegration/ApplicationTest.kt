package com.rhuertas.kmpwebsocketsintegration

import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlin.test.*

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello, Ktor!", response.bodyAsText())
    }

    @Test
    fun testWebSocketEcho() = testApplication {
        application {
            module()
        }

        val webSocketClient = createClient {
            install(WebSockets)
        }

        webSocketClient.webSocket("/ws") {
            assertEquals("Connected", (incoming.receive() as Frame.Text).readText())

            send("hello")
            assertEquals("Echo: hello", (incoming.receive() as Frame.Text).readText())

            send("bye")
            assertEquals(
                CloseReason(CloseReason.Codes.NORMAL, "Client closed"),
                closeReason.await(),
            )
        }
    }
}