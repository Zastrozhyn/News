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
import ru.clevertec.ecl.web.dto.NewsDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@ExtendWith({WireMockExtension.class, MockitoExtension.class})
public class NewsControllerTest extends BaseIntegrationTest {

    private static final Long NEWS_ID = 1L;
    private static final int AMOUNT_OF_ALL_NEWS = 20;
    private static final String BASIC_URL = "/news";

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public NewsControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void checkFindNewsById() throws Exception {
        mockMvc.perform(get(BASIC_URL + "/" + NEWS_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(NEWS_ID));
    }

    @Test
    void checkFindAllNews() throws Exception {
        mockMvc.perform(get(BASIC_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(AMOUNT_OF_ALL_NEWS));
    }

    @Test
    void checkFindAllNewsWithSearch() throws Exception {
        String searchPart = "?search=+&text=text";
        mockMvc.perform(get(BASIC_URL + searchPart))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(AMOUNT_OF_ALL_NEWS));
    }

    @Test
    void checkUpdateNews() throws Exception {
        String updatedText = "new text";
        String updatedTitle = "new title";
        NewsDto newsDto = new NewsDto();
        newsDto.setText(updatedText);
        newsDto.setTitle(updatedTitle);
        newsDto.setId(NEWS_ID);

        mockMvc.perform(put(BASIC_URL + "/" + NEWS_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(NEWS_ID))
                .andExpect(jsonPath("$.text").value(updatedText))
                .andExpect(jsonPath("$.title").value(updatedTitle));
    }

    @Test
    void checkCreateNews() throws Exception {
        String text = "new text";
        String title = "new title";
        NewsDto newsDto = new NewsDto();
        newsDto.setText(text);
        newsDto.setTitle(title);

        mockMvc.perform(post(BASIC_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.text").value(text))
                .andExpect(jsonPath("$.title").value(title));
    }

    @Test
    void checkDeleteNews() throws Exception {
        mockMvc.perform(delete(BASIC_URL + "/" + NEWS_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void checkDeleteComment() throws Exception {
        String commentId = "1";
        mockMvc.perform(delete(BASIC_URL + "/" + NEWS_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentId))
                .andExpect(status().isNoContent());
    }
}
