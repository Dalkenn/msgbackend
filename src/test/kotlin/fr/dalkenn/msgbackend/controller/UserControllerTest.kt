package fr.dalkenn.msgbackend.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class UserControllerTest() {
    companion object {
        @Container
        @ServiceConnection
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
    }

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun shouldCreateUserWithId1() {
        mockMvc.perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
                        {
                          "userName": "Foo"
                        }
                    """,
                ),
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.user.userName").value("Foo"))
            .andExpect(jsonPath("$.user.id").exists())
    }

    @Test
    fun shouldReturn400() {
        mockMvc.perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
                        {
                          "userName": "Bar"
                        }
                    """,
                ),
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.user.userName").value("Bar"))

        mockMvc.perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
                    {
                      "userName": "Bar"
                    }   
                    """,
                ),
        )
            .andDo(print())
            .andExpect(status().isConflict())
            .andExpect(content().string("User name already exists."))
    }
}
