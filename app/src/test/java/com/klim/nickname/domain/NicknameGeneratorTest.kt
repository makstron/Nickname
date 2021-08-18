package com.klim.nickname.domain

import com.klim.matchers.MatchersGetter.isLongerThan
import com.klim.matchers.MatchersGetter.isShorterThan
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import java.util.*

internal class NicknameGeneratorTest {

    private lateinit var nicknameGenerator: NicknameGenerator

    @BeforeEach
    fun setUp() {
        nicknameGenerator = NicknameGenerator()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `nickname fixed length`() {
        for (requiredLength in 0..100) {
            for (repeatCount in 0..10) {
                val nickname = nicknameGenerator.createName(0, requiredLength, true)
                assertThat(nickname.length, `is`(equalTo(requiredLength)))
            }
        }
    }

    @Test
//    @RepeatedTest(10)
    internal fun `nickname in some borders`() {
        val gap = 10
        for (maxLength in gap..100) {
            for (minLength in 0..maxLength - gap) {
                for (repeatCount in 0..10) {
                    val nickname = nicknameGenerator.createName(minLength, maxLength, false)
                    assertThat(nickname, `is`(allOf(isLongerThan(minLength), isShorterThan(maxLength))))
                }
            }
        }
    }
}