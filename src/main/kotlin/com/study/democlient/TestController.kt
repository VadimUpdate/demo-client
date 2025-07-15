package com.example.demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RefreshScope
@RestController
class TestController {

    @Value("\${20}")
    private var maxConnections: Int = 0

    @Value("\${2}")
    private var enableLogging: Boolean = false

    @GetMapping("/test")
    fun testEndpoint(): String {
        return "Max connections: $maxConnections, Logging enabled: $enableLogging"
    }
}
