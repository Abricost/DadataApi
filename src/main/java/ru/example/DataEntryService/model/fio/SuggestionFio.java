package ru.example.DataEntryService.model.fio;

import lombok.Data;

@Data
public class SuggestionFio {
    private String value;
    private String unrestricted_value;
    private DataFio data;
}
