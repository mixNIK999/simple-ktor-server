package me.nikoliukin.mikhail.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.request.*
import me.nikoliukin.mikhail.Generators
import me.nikoliukin.mikhail.RequestLog

fun Application.configureRouting() {

    routing {
        RequestLog.clearLog()
        get("/gen_string") {
            call.request.queryParameters["length"]?.toIntOrNull()?.let{ if (it > 0) it  else null }?.let {
                val res = Generators.genString(it)
                RequestLog.writeToLog(res)
                call.respondText(res)
            } ?: call.response.status(HttpStatusCode.BadRequest)
        }
        get("/gen_int") {
            val left = call.request.queryParameters["left"]?.toIntOrNull()
            val right = call.request.queryParameters["right"]?.toIntOrNull()
            if (left != null && right != null && left < right) {
                val res = Generators.genInt(left, right).toString()
                RequestLog.writeToLog(res)
                call.respondText(res)
            } else {
                call.response.status(HttpStatusCode.BadRequest)
            }
        }
        get("/requests.log") {
            call.respondFile(RequestLog.getFile())
        }
    }
}
