package ru.clevertec.ecl.integrationtest.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.integrationtest.BaseIntegrationTest;
import ru.clevertec.ecl.integrationtest.WireMockExtension;
import ru.clevertec.ecl.web.dto.UserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@ExtendWith({WireMockExtension.class, MockitoExtension.class})
public class UserControllerTest extends BaseIntegrationTest {
    private static final Long USER_ID = 1L;
    private static final int AMOUNT_OF_USERS = 3;
    private static final String BASIC_URL = "/users";

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }


    @Test
    void checkFindUserById() throws Exception {
        mockMvc.perform(get(BASIC_URL + "/" + USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(USER_ID));
    }

    @Test
    void checkFindAllUser() throws Exception {
        mockMvc.perform(get(BASIC_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(AMOUNT_OF_USERS));
    }

    @Test
    void checkUpdateUser() throws Exception {
        String updatedName = "NewName";
        UserDto userDto = new UserDto();
        userDto.setId(USER_ID);
        userDto.setName(updatedName);

        mockMvc.perform(put(BASIC_URL + "/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.name").value(updatedName));
    }

    @Test
    void checkCreateUser() throws Exception {
        String name = "Name";
        UserDto userDto = new UserDto();
        userDto.setName(name);

        mockMvc.perform(post(BASIC_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void checkDeleteUser() throws Exception {
        mockMvc.perform(delete(BASIC_URL + "/" + USER_ID))
                .andExpect(status().isNoContent());
    }

}
