package ru.example.DataEntryService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.example.DataEntryService.api.DadataApi;
import ru.example.DataEntryService.model.address.RootAddress;
import ru.example.DataEntryService.model.fio.NameRequest;
import ru.example.DataEntryService.model.fio.RootFio;

import java.io.File;
import java.net.URL;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DadataApi dadataApi;

    @Test
    public void fioTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        NameRequest nameRequest = new NameRequest();
        nameRequest.setQuery("Анд");
        nameRequest.setCount(1);
        String requestJson = objectMapper.writeValueAsString(nameRequest);

        String body = getStringFromJsonFile("dadata_fio_response.json");

        RootFio rootFio = objectMapper.readValue(body, RootFio.class);

        when(dadataApi.sendRequest("Анд", 1, RootFio.class)).thenReturn(rootFio);

        mockMvc.perform(post("/api/person/suggest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Андрей"))
                .andExpect(jsonPath("$[0].gender").value("MALE"));
    }

    @Test
    public void addressTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String body = getStringFromJsonFile("dadata_addres_response.json");
        RootAddress rootAddress = objectMapper.readValue(body, RootAddress.class);

        when(dadataApi.sendRequest("Мос", 1, RootAddress.class)).thenReturn(rootAddress);

        mockMvc.perform(get("/api/address/suggest")
                        .param("query", "Мос")
                        .param("count", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullAddress").value("101000, г Москва"))
                .andExpect(jsonPath("$[0].country").value("Россия"))
                .andExpect(jsonPath("$[0].city").value("Москва"))
                .andExpect(jsonPath("$[0].postalCode").value("101000"));
    }

    private String getStringFromJsonFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            URL resourceUrl = MockTest.class.getResource("/" + filePath);
            assert resourceUrl != null;
            File file = new File(resourceUrl.getFile());
            Object json = objectMapper.readValue(file, Object.class);
            return objectMapper.writeValueAsString(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
