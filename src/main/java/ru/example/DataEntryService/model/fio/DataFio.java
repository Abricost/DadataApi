package ru.example.DataEntryService.model.fio;

import lombok.Data;

@Data
public class DataFio {
    private String surname;
    private String name;
    private String patronymic;
    private String gender;
    private String source;
    private String qc;
}
