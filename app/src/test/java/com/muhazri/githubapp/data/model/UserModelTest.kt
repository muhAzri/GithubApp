package com.muhazri.githubapp.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class UserSearchResponseTest {

    @Test
    fun testUserSearchResponse() {
        val totalCount = 100
        val incompleteResults = false
        val items = listOf(
            User(1, "user1", "https://example.com/avatar1"),
            User(2, "user2", "https://example.com/avatar2")
            // Add more users if needed
        )

        val userSearchResponse = UserSearchResponse(totalCount, incompleteResults, items)

        assertEquals(totalCount, userSearchResponse.totalCount)
        assertEquals(incompleteResults, userSearchResponse.incompleteResults)
        assertEquals(items.size, userSearchResponse.items.size)
        // Additional assertions can be added for each item in the list
    }

    @Test
    fun testUser() {
        val id = 1
        val login = "user1"
        val avatarUrl = "https://example.com/avatar1"

        val user = User(id, login, avatarUrl)

        assertEquals(id, user.id)
        assertEquals(login, user.login)
        assertEquals(avatarUrl, user.avatarUrl)
    }
}
