package com.rhuertas.kmpwebsocketsintegration

import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
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
            val expected = listOf(
                Task("cleaning", "Clean the house", Priority.Low),
                Task("gardening", "Mow the lawn", Priority.Medium),
                Task("shopping", "Buy the groceries", Priority.High),
                Task("painting", "Paint the fence", Priority.Medium),
            )

            expected.forEach { task ->
                val frame = incoming.receive() as Frame.Text
                assertEquals(task, Json.decodeFromString(frame.readText()))
            }

            assertEquals(CloseReason(CloseReason.Codes.NORMAL, "All done"), closeReason.await())
        }
    }
}