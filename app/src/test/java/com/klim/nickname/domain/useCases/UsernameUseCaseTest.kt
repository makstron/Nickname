package com.klim.nickname.domain.useCases

import com.klim.nickname.domain.NicknameGenerator
import com.klim.nickname.domain.repositories.nickname.UserNameRepositoryI
import com.klim.nickname.domain.repositories.nickname.models.NicknameEntity
import com.klim.nickname.utils.UID
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

internal class UsernameUseCaseTest {

    lateinit var useCase: UsernameUseCase
    lateinit var repository: UserNameRepositoryI
    lateinit var nicknameGenerator: NicknameGenerator

    @BeforeEach
    fun setUp() {
        repository = Mockito.mock(UserNameRepositoryI::class.java)
        nicknameGenerator = Mockito.mock(NicknameGenerator::class.java)
        useCase = UsernameUseCase(repository, nicknameGenerator)
    }

    private fun generateSavedList(): List<NicknameEntity> {
        return listOf(
            createRandomNickname(),
            createRandomNickname())
    }

    private fun createRandomNickname(): NicknameEntity = NicknameEntity(UID.randomUID(), "Aloise", System.currentTimeMillis())

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `generating new nickname`() {
        val nickname = "Joh"
        val nicknameMinLength = 3
        val nicknameMaxLength = 7
        Mockito.`when`(nicknameGenerator.createName(nicknameMinLength, nicknameMaxLength, false)).thenReturn(nickname)

        val newNickname = useCase.getNewUserName(nicknameMinLength, nicknameMaxLength, false)
        assertThat(newNickname.name, equalTo(nickname))
        assertThat(newNickname.time, allOf(notNullValue(), greaterThan(0)))
        assertThat(newNickname.id, notNullValue())
    }

    @Test
    fun `saving nickname`() {
        val nickname = createRandomNickname()
        useCase.save(nickname)

        verify(repository).save(nickname)
    }

    @DisplayName("\uD83D\uDC4D  get all saved")
    @Test
    fun `get all saved`() {
        val listSavedNicknames: List<NicknameEntity> = generateSavedList()
        Mockito.`when`(repository.getAll()).thenReturn(listSavedNicknames)

        val savedNicknames = useCase.getAllSaved()
        assertIterableEquals(savedNicknames, listSavedNicknames)
    }
}