plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ktor)
}

group = "com.rhuertas.kmpwebsocketsintegration"
version = "1.0.0"
application {
    mainClass = "com.rhuertas.kmpwebsocketsintegration.ApplicationKt"
}

dependencies {
    api(project(":core"))
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serializationKotlinx)
    implementation(libs.ktor.serializationKotlinxJson)
    implementation(libs.ktor.serverNetty)
    implementation(libs.ktor.serverWebSockets)
    testImplementation(libs.ktor.clientWebSockets)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}