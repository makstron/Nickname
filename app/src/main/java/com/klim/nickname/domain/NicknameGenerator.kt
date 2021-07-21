package com.klim.nickname.domain

import kotlin.random.Random

object NicknameGenerator {

    /**
     * Generate new random name
     * If @param fixedLength is true then @param minLength does not used
     */
    fun createName(minLength: Int, maxLength: Int, fixedLength: Boolean = false): String {
        //        val alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray()
        val vowels = "bcdfghjklmnpqrstvwxyz".toCharArray()
        val consonants = "aeiouy".toCharArray()
        var letterType = Random.nextBoolean()
        var name = ""
        var length = if (fixedLength) maxLength else minLength + Random.nextInt(maxLength - minLength+1)
        for (i in 0 until length) {
            if (letterType) {
                name += vowels[Random.nextInt(vowels.size)]
            } else {
                name += consonants[Random.nextInt(consonants.size)]
            }
            if (i == 0) {
                name = name.toUpperCase()
            }
            letterType = !letterType
        }

        return name
    }

}