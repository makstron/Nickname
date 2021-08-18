package com.klim.nickname.domain

import java.lang.StringBuilder
import javax.inject.Inject
import kotlin.random.Random


class NicknameGenerator
@Inject
constructor() {

    val vowels = "bcdfghjklmnpqrstvwxyz".toCharArray()
    val consonants = "aeiouy".toCharArray()

    val name = StringBuilder()

    /**
     * Generate new random name
     * If @param fixedLength is true then @param minLength will not use
     */
    fun createName(minLength: Int, maxLength: Int, fixedLength: Boolean = false): String {
        val length = getNameLength(minLength, maxLength, fixedLength)
        var letterIsVowel = Random.nextBoolean()

        clearName()
        for (position in 0 until length) {
            name.append(getNextLetter(position, letterIsVowel))
            letterIsVowel = !letterIsVowel
        }

        return name.toString()
    }

    private fun getNameLength(minLength: Int, maxLength: Int, fixedLength: Boolean): Int {
        return if (fixedLength)
            maxLength
        else
            minLength + Random.nextInt(maxLength - minLength + 1)
    }

    private fun clearName() {
        name.setLength(0)
        name.trimToSize()
    }

    private fun getNextLetter(position: Int, letterIsVowel: Boolean): Char {
        return selectLetter(letterIsVowel).run {
            setUpperCaseIfNeed(position, this)
        }
    }

    private fun selectLetter(letterIsVowel: Boolean): Char {
        return if (letterIsVowel) {
            vowels[Random.nextInt(vowels.size)]
        } else {
            consonants[Random.nextInt(consonants.size)]
        }
    }

    private fun setUpperCaseIfNeed(position: Int, letter: Char): Char {
        if (position == 0) {
            return letter.uppercaseChar()
        }
        return letter
    }

    public fun getLastGenerated() = name.toString()
}