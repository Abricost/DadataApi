package ru.example.DataEntryService.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.example.DataEntryService.model.RootDadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DadataApi {
    private final RestTemplate restTemplate;

    public DadataApi() {
        this.restTemplate = new RestTemplate();
    }

    @Value("${dadata.api.key}")
    private String dadataApiKey;
    @Value("${dadata.url}")
    private String dadataURL;
    private final static String DADADA_SUGGEST_PATH = "/suggestions/api/4_1/rs/suggest/";

    public <T extends RootDadata> T sendRequest(final String query, final int count, Class<T> clazz) {
        String requestType = null;
        try {
            requestType = (String) clazz.getField("DADATA_REQUIRED_TYPE").get(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = dadataURL + DADADA_SUGGEST_PATH + requestType;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Token " + dadataApiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        requestBody.put("count", count);

        return restTemplate.postForObject(url, new HttpEntity<>(requestBody, headers), clazz);
    }
}
