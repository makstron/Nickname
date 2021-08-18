package com.klim.nickname.data.repositories.userName

import com.google.common.truth.Truth.assertThat
import com.klim.nickname.data.db.tables.Nickname
import com.klim.nickname.data.dto.UserNameDTO
import com.klim.nickname.data.repositories.userName.dataSources.local.LocalDataSource
import com.klim.nickname.data.repositories.userName.mappers.map
import com.klim.nickname.domain.repositories.nickname.models.NicknameEntity
import com.klim.nickname.utils.UID
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.*

internal class UserNameRepositoryTest {

    private lateinit var repository: UserNameRepository
    private lateinit var localStore: LocalDataSource

    private val listNicknames by lazy {
        listOf(createRandomNicknameDTO(), createRandomNicknameDTO())
    }

    @BeforeEach
    fun setUp() {
        localStore = mock<LocalDataSource> {
            on { getAll() } doReturn listNicknames
        }
        repository = UserNameRepository(localStore)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `test save nickname`() {
        val nickname = createRandomNickname()
        repository.save(nickname)

        verify(localStore).save(any())
    }

    private fun createRandomNickname(): NicknameEntity = NicknameEntity(UID.randomUID(), "Aloise", System.currentTimeMillis())

    private fun createRandomNicknameDTO(): UserNameDTO = UserNameDTO(UID.randomUID(), "Aloise", System.currentTimeMillis())

    @Test
    fun getAll() {
        val expectedNames = listNicknames.map { it.map() }
        val nicknames = repository.getAll()
        assertThat(nicknames).containsExactlyElementsIn(expectedNames)
    }
}