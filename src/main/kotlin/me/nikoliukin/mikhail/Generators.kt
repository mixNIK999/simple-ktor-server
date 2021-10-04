package me.nikoliukin.mikhail

import kotlin.random.Random

object Generators {

    private val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun genString(len: Int) = List(len) { charset.random() }.joinToString("")

    fun genInt(left: Int, right: Int) = Random.nextInt(left, right)
}