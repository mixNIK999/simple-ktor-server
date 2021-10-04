package me.nikoliukin.mikhail

import io.ktor.http.*
import io.ktor.server.testing.*
import me.nikoliukin.mikhail.plugins.configureRouting
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {

    @Test
    fun testGenIntFromRange() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/gen_int?left=10&right=20").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val num = response.content!!.toInt()
                assertTrue { num in 10..20 }
            }
        }
    }

    @Test
    fun testGenIntFromPoint() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/gen_int?left=100&right=101").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val num = response.content!!.toInt()
                assertEquals(100, num)
            }
        }
    }

    @Test
    fun testGenIntBedRange() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/gen_int?left=100&right=100").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }

    @Test
    fun testGenIntBedParams() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/gen_int?left=0&right=100a").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }

    @Test
    fun testGenString() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/gen_string?length=1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(1, response.content?.length)
            }
        }
    }

    @Test
    fun testGenStringLong() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/gen_string?length=10000").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(10000, response.content?.length)
            }
        }
    }

    @Test
    fun testGenStringWithZeroLength() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/gen_string?length=0").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }

    @Test
    fun testGenStringWithNegativeLength() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/gen_string?length=-20").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }

    @Test
    fun testGenStringWithBedLength() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/gen_string?length=10a").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }

    @Test
    fun testGetLog() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/requests.log").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("", response.content)
            }
            handleRequest(HttpMethod.Get, "/gen_string?length=10")
            handleRequest(HttpMethod.Get, "/gen_int?left=10&right=20")
            handleRequest(HttpMethod.Get, "/requests.log").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(3, response.content!!.lines().size)
            }
        }
    }
}