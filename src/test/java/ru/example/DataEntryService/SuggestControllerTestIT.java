package ru.example.DataEntryService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import ru.example.DataEntryService.model.address.AddressResponse;
import ru.example.DataEntryService.model.fio.enums.Gender;
import ru.example.DataEntryService.model.fio.NameRequest;
import ru.example.DataEntryService.model.fio.NameResponse;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Profile("test")
public class SuggestControllerTestIT extends AbstractIT{
    @Autowired
    private RestTemplate defaultRestTemplate;

    @Value("${rest_integration_host}")
    private String HOST;

    @Test
    public void suggestPersonTest() throws JsonProcessingException {
        NameResponse nameResponse = new NameResponse();
        nameResponse.setFullName("Андрей");
        nameResponse.setGender(Gender.MALE);

        NameRequest nameRequest = new NameRequest();
        nameRequest.setQuery("Ан");
        nameRequest.setCount(1);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        String personEndpoint = "/api/person/suggest";
        String response = defaultRestTemplate.postForObject(HOST + personEndpoint, new HttpEntity<>(nameRequest, httpHeaders), String.class);

        assertEquals(List.of(nameResponse), toNameResponse(response));
    }

    @Test
    public void suggestAddressTest() throws JsonProcessingException {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setFullAddress("101000, г Москва");
        addressResponse.setCountry("Россия");
        addressResponse.setCity("Москва");
        addressResponse.setPostalCode("101000");

        String query = "Мос";
        int count = 1;
        String addressEndpoint = "/api/address/suggest";
        String fullUrl = HOST + addressEndpoint + "?query=" + query + "&count=" + count;

        String response = defaultRestTemplate.getForObject(fullUrl, String.class );

        assertEquals(List.of(addressResponse), toAddressResponse(response));
    }

    private List<NameResponse> toNameResponse(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return  Arrays.asList(mapper.readValue(response, NameResponse[].class));
    }

    private List<AddressResponse> toAddressResponse(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return  Arrays.asList(mapper.readValue(response, AddressResponse[].class));
    }
}
