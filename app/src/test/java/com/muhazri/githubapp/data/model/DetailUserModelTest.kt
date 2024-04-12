package com.muhazri.githubapp.data.model

import org.junit.Assert.assertEquals
import org.junit.Test


    class DetailUserModelTest {

        @Test
        fun testDetailUserModel() {
            val id = 1
            val login = "user1"
            val avatarUrl = "https://example.com/avatar1"
            val name = "John Doe"
            val location = "New York"
            val followers = 100

            val detailUserModel = DetailUserModel(id, login, avatarUrl, name, location, followers)

            assertEquals(id, detailUserModel.id)
            assertEquals(login, detailUserModel.login)
            assertEquals(avatarUrl, detailUserModel.avatarUrl)
            assertEquals(name, detailUserModel.name)
            assertEquals(location, detailUserModel.location)
            assertEquals(followers, detailUserModel.followers)
        }
    }

