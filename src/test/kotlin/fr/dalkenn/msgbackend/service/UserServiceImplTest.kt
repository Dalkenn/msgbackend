package fr.dalkenn.msgbackend.service

import fr.dalkenn.msgbackend.db.User
import fr.dalkenn.msgbackend.db.UserRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import org.junit.Assert.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserServiceImplTest {
    private val repository: UserRepository = mockk()

    @InjectMockKs
    val userService = UserServiceImpl(repository)

    @Test
    fun createNewUser() {
        // Given
        val result = User("Foo", 1, null, null)
        every { repository.existsByUserName("Foo") } returns false
        every { repository.save(User("Foo", null, null, null)) } returns result

        // When
        val createdUser = userService.createNewUser("Foo")

        // Then
        assertEquals(createdUser, result.toUserDto())
    }

    @Test
    fun shouldThrowExceptionUserAlreadyExist() {
        // Given
        every { repository.existsByUserName("Bar") } returns true

        // Then
        assertThrows("User name already exists.", IllegalArgumentException::class.java) {
            userService.createNewUser("Bar")
        }
    }

    @Test
    fun shouldThrowExceptionUsernameCannotBeBlank() {
        assertThrows("Username cannot be blank.", IllegalArgumentException::class.java) {
            userService.createNewUser("  ")
        }
    }

    @Test
    fun shouldThrowExceptionUsernameCannotContainSpecialCharacters() {
        assertThrows("Username can contain only letters or numbers.", IllegalArgumentException::class.java) {
            userService.createNewUser(" Test&% ")
        }
    }

    @Test
    fun shouldThrowIllegalArgumentException() {
        // Given
        every { repository.findUserById(1) } returns null

        assertThrows("No user found with user id 1.", IllegalArgumentException::class.java) {
            userService.findUserById(1)
        }
    }
}
