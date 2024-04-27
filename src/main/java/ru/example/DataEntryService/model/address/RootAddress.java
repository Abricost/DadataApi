package ru.example.DataEntryService.model.address;

import lombok.Data;
import ru.example.DataEntryService.model.RootDadata;

import java.util.List;

@Data
public class RootAddress extends RootDadata {
    public static final String DADATA_REQUIRED_TYPE = "address";
    private List<SuggestionAddress> suggestions;
}