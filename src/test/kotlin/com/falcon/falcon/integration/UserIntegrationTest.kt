package com.falcon.falcon.integration

import com.falcon.falcon.asString
import com.falcon.falcon.dataprovider.persistence.user.UserEntity
import com.falcon.falcon.dataprovider.persistence.user.UserRepository
import com.falcon.falcon.entrypoint.rest.user.UserRequest
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var repository: UserRepository

    @BeforeEach
    fun clean() {
        repository.deleteAll()
    }

    @Test
    fun `Should create user`() {
        val request = UserRequest("first-username", "second-username")

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/user")
                .content(request.asString())
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        repository.findByUsername(request.username!!).shouldNotBeNull()
    }

    @Test
    fun `Should return conflict if user already exists`() {
        val request = UserRequest("first-username", "second-username")
        repository.save(UserEntity(request.username!!))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/user")
                .content(request.asString())
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isConflict)
    }
}
