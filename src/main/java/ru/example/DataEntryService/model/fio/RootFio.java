package ru.example.DataEntryService.model.fio;

import lombok.Data;
import ru.example.DataEntryService.model.RootDadata;

import java.util.List;

@Data
public class RootFio extends RootDadata {
    public static final String DADATA_REQUIRED_TYPE = "fio";
    private List<SuggestionFio> suggestions;
}
