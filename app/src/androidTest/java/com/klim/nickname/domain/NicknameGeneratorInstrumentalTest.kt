package com.klim.nickname.domain

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.*
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class NicknameGeneratorInstrumentalTest {

    private lateinit var nicknameGenerator: NicknameGenerator

    @BeforeEach
    fun setUp() {
        nicknameGenerator = NicknameGenerator()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun createNameFixedLength() {
        val requiredLength = 10
        val nickname = nicknameGenerator.createName(0, requiredLength, true)
        assertEquals(requiredLength, nickname.length)
    }
}