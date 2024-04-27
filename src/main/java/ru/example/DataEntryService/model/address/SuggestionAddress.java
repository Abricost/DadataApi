package ru.example.DataEntryService.model.address;

import lombok.Data;

@Data
public class SuggestionAddress {
    private String value;
    private String unrestricted_value;
    private DataAddress data;
}
