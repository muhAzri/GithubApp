package com.muhazri.githubapp.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class FavouriteUserModelTest {

    @Test
    fun testFavouriteUserModel() {
        val id = 1
        val login = "user1"
        val avatarUrl = "https://example.com/avatar1"

        val favouriteUserModel = FavouriteUserModel(id, login, avatarUrl)

        assertEquals(id, favouriteUserModel.id)
        assertEquals(login, favouriteUserModel.login)
        assertEquals(avatarUrl, favouriteUserModel.avatarUrl)
    }
}
