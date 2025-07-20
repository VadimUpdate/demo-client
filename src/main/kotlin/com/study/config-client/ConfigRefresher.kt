package com.example.demo

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class ConfigRefresher {

    private val client = HttpClient.newHttpClient()
    private val refreshUri = URI.create("http://localhost:8081/actuator/refresh")

    @PostConstruct
    fun startAutoRefresh() {
        val scheduler = Executors.newSingleThreadScheduledExecutor()

        scheduler.scheduleAtFixedRate({
            try {
                val request = HttpRequest.newBuilder()
                    .uri(refreshUri)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build()

                val response = client.send(request, HttpResponse.BodyHandlers.ofString())
                println("Refreshed config: ${response.body()}")
            } catch (ex: Exception) {
                println(" Failed to refresh config: ${ex.message}")
            }
        }, 0, 15, TimeUnit.SECONDS)
    }
}
