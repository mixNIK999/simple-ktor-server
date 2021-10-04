package me.nikoliukin.mikhail

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import io.ktor.application.*
import io.ktor.content.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import me.nikoliukin.mikhail.plugins.*

class Application : CliktCommand() {

    private val port: Int by option(help = "Server port number").int().default(7531)

    override fun run() {
        embeddedServer(Netty, port = port, host = "0.0.0.0") {
            configureRouting()
            install(StatusPages) {
                status(HttpStatusCode.BadRequest) {
                    call.respond(
                        TextContent(
                            "${it.value} ${it.description}",
                            ContentType.Text.Plain.withCharset(Charsets.UTF_8),
                            it
                        )
                    )
                }
            }
        }.start(wait = true)
    }
}

fun main(args: Array<String>) = Application().main(args)
