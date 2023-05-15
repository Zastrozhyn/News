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
import ru.clevertec.ecl.web.dto.CommentDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith({WireMockExtension.class, MockitoExtension.class})
public class CommentControllerTest extends BaseIntegrationTest {

    private static final Long COMMENT_ID = 1L;
    private static final int AMOUNT_OF_ALL_COMMENT = 10;
    private static final String BASIC_URL = "/comments";

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public CommentControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void checkFindCommentById() throws Exception {
        mockMvc.perform(get(BASIC_URL + "/" + COMMENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(COMMENT_ID));
    }

    @Test
    void checkFindAllComment() throws Exception {
        mockMvc.perform(get(BASIC_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(AMOUNT_OF_ALL_COMMENT));
    }

    @Test
    void checkFindAllCommentWithSearch() throws Exception {
        String searchPart = "?search=+&text=comment";
        mockMvc.perform(get(BASIC_URL + searchPart))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(AMOUNT_OF_ALL_COMMENT));
    }

    @Test
    void checkUpdateComment() throws Exception {
        String updatedText = "new text";
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setText(updatedText);

        mockMvc.perform(put(BASIC_URL + "/" + COMMENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(COMMENT_ID))
                .andExpect(jsonPath("$.text").value(updatedText));
    }

}
