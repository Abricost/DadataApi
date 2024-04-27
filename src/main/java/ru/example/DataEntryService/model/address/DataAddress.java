package ru.example.DataEntryService.model.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataAddress {
    public String postal_code;
    public String country;
    public String region_with_type;
    public String city;
}