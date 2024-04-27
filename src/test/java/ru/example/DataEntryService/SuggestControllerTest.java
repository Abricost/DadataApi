package ru.example.DataEntryService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.example.DataEntryService.model.address.AddressResponse;
import ru.example.DataEntryService.model.fio.enums.Gender;
import ru.example.DataEntryService.model.fio.NameRequest;
import ru.example.DataEntryService.model.fio.NameResponse;
import ru.example.DataEntryService.service.DadataService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SuggestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DadataService dadataService;

    @Test
    public void fioTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        NameResponse nameResponse = new NameResponse();
        nameResponse.setFullName("Иван");
        nameResponse.setGender(Gender.MALE);

        NameRequest nameRequest = new NameRequest();
        nameRequest.setQuery("Ива");
        nameRequest.setCount(1);
        String requestJson = objectMapper.writeValueAsString(nameRequest);

        when(dadataService.suggestName("Ива", 1)).thenReturn(List.of(nameResponse));

        mockMvc.perform(post("/api/person/suggest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Иван"))
                .andExpect(jsonPath("$[0].gender").value("MALE"));
    }

    @Test
    public void addressTest() throws Exception {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setFullAddress("a");
        addressResponse.setCountry("b");
        addressResponse.setCity("c");
        addressResponse.setPostalCode("d");

        when(dadataService.suggestAddress("Мос", 1)).thenReturn(List.of(addressResponse));

        mockMvc.perform(get("/api/address/suggest")
                .param("query", "Мос")
                .param("count", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullAddress").value("a"))
                .andExpect(jsonPath("$[0].country").value("b"))
                .andExpect(jsonPath("$[0].city").value("c"))
                .andExpect(jsonPath("$[0].postalCode").value("d"));
    }
}
