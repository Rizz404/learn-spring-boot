package com.rizz.learn.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class AuthIntegrationTest {

  @Autowired private MockMvc mockMvc;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void register_withValidData_shouldReturnTokenAndUserInfo() throws Exception {
    // * Jadi json
    String requestBody =
        """
        {
            "email": "test@example.com",
            "password": "password123",
            "name": "Test User"
        }
        """;

    mockMvc
        .perform(
            post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.email").value("test@example.com"))
        .andExpect(jsonPath("$.name").value("Test User"))
        .andExpect(jsonPath("$.role").value("USER"));
  }

  @Test
  void register_withDuplicateEmail_shouldReturn400() throws Exception {
    String requestBody =
        """
        {
            "email": "duplicate@example.com",
            "password": "password123",
            "name": "First User"
        }
        """;

    // * Register pertama — harus berhasil
    mockMvc
        .perform(
            post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isCreated());

    // * Register kedua dengan email sama — harus 400
    mockMvc
        .perform(
            post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  void login_withValidCredentials_shouldReturnToken() throws Exception {
    // * Register dulu
    String registerBody =
        """
        {
            "email": "login@example.com",
            "password": "password123",
            "name": "Login User"
        }
        """;
    mockMvc
        .perform(
            post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerBody))
        .andExpect(status().isCreated());

    // * Login
    String loginBody =
        """
        {
            "email": "login@example.com",
            "password": "password123"
        }
        """;
    mockMvc
        .perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(loginBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.type").value("Bearer"));
  }

  @Test
  void login_withWrongPassword_shouldReturn401() throws Exception {
    // * Register dulu
    String registerBody =
        """
        {
            "email": "wrongpass@example.com",
            "password": "password123",
            "name": "Wrong Pass User"
        }
        """;
    mockMvc
        .perform(
            post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerBody))
        .andExpect(status().isCreated());

    // * Login dengan password salah
    String loginBody =
        """
        {
            "email": "wrongpass@example.com",
            "password": "salahpassword"
        }
        """;
    mockMvc
        .perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(loginBody))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getProducts_withoutToken_shouldReturn401() throws Exception {
    mockMvc.perform(get("/api/products")).andExpect(status().isUnauthorized());
  }

  @Test
  void getProducts_withValidToken_shouldReturn200() throws Exception {
    // * Register & dapat token
    String registerBody =
        """
        {
            "email": "tokentest@example.com",
            "password": "password123",
            "name": "Token Test User"
        }
        """;
    MvcResult result =
        mockMvc
            .perform(
                post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(registerBody))
            .andExpect(status().isCreated())
            .andReturn();

    // * Extract token dari response
    String responseJson = result.getResponse().getContentAsString();
    String token = objectMapper.readTree(responseJson).get("token").asText();

    // * Akses /api/products dengan token
    mockMvc
        .perform(get("/api/products").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
  }
}
