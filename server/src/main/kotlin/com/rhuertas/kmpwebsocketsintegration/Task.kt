package com.rhuertas.kmpwebsocketsintegration

import io.ktor.server.websocket.WebSocketServerSession
import io.ktor.server.websocket.receiveDeserialized
import io.ktor.server.websocket.sendSerialized
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.milliseconds

enum class Priority {
    Low, Medium, High, Vital
}

@Serializable
data class Task(
    val name: String,
    val description: String,
    val priority: Priority
)

suspend fun WebSocketServerSession.handleClient() {
    val tasks = listOf(
        Task("cleaning", "Clean the house", Priority.Low),
        Task("gardening", "Mow the lawn", Priority.Medium),
        Task("shopping", "Buy the groceries", Priority.High),
        Task("painting", "Paint the fence", Priority.Medium),
    )

    for (task in tasks) {
        sendSerialized(task)
        delay(1000.milliseconds)
    }

    outgoing.send(Frame.Close(CloseReason(CloseReason.Codes.NORMAL, "All done")))
}