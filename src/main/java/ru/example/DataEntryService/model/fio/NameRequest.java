package ru.example.DataEntryService.model.fio;

import lombok.Data;

@Data
public class NameRequest {
    private String query;
    private int count;

}
