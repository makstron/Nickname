package com.klim.nickname.domain.useCases

import com.klim.nickname.domain.models.UserName
import com.klim.nickname.domain.repositories.UserNameRepositoryI
import javax.inject.Inject
import kotlin.random.Random

class UsernameUseCase
constructor(private val repositoryI: UserNameRepositoryI) {

    /**
     * Generate new random name
     * If @param fixedLength is true then @param minLength do not used
     */
    fun getNewUserName(minLength: Int, maxLength: Int, fixedLength: Boolean = false): UserName {
//        val alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray()
        val vowels = "bcdfghjklmnpqrstvwxyz".toCharArray()
        val consonants = "aeiouy".toCharArray()
        var letterType = Random.nextBoolean()
        var name = ""
        var length = if (fixedLength) maxLength else minLength + Random.nextInt(maxLength - minLength)
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
        return UserName(name, System.currentTimeMillis())
    }

    fun save(userName: UserName) {
        repositoryI.save(userName)
    }

    fun getAllSaved(): ArrayList<UserName> {
        return repositoryI.getAll()
    }

}