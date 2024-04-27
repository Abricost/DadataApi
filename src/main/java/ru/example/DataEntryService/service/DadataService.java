package ru.example.DataEntryService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.DataEntryService.api.DadataApi;
import ru.example.DataEntryService.model.address.AddressResponse;
import ru.example.DataEntryService.model.address.RootAddress;
import ru.example.DataEntryService.model.address.SuggestionAddress;
import ru.example.DataEntryService.model.fio.enums.Gender;
import ru.example.DataEntryService.model.fio.NameResponse;
import ru.example.DataEntryService.model.fio.RootFio;
import ru.example.DataEntryService.model.fio.SuggestionFio;

import java.util.ArrayList;
import java.util.List;

@Service
public class DadataService {

    @Autowired
    private DadataApi dadataApi;

    public List<NameResponse> suggestName(String query, int count) {
        RootFio response = dadataApi.sendRequest(query, count, RootFio.class);
        return toNameResponseList(response);
    }

    public List<AddressResponse> suggestAddress(String query, int count) {
        RootAddress response =  dadataApi.sendRequest(query, count, RootAddress.class);
        return toAddressResponseList(response);
    }

    private List<NameResponse> toNameResponseList(RootFio rootFio) {
        List<NameResponse> nameResponseList = new ArrayList<>();
        for (SuggestionFio suggestionFio : rootFio.getSuggestions()) {
            String value = suggestionFio.getValue();
            Gender gender = Gender.valueOf(suggestionFio.getData().getGender());
            nameResponseList.add(new NameResponse(value, gender));
        }
        return nameResponseList;
    }

    private List<AddressResponse> toAddressResponseList(RootAddress rootAddress) {
        List<AddressResponse> addressResponseList = new ArrayList<>();
        for (SuggestionAddress suggestionAddress : rootAddress.getSuggestions()) {
            String postalCode = suggestionAddress.getData().getPostal_code();
            String country = suggestionAddress.getData().getCountry();
            String city = suggestionAddress.getData().getCity();
            String unrestrictedValue = suggestionAddress.getUnrestricted_value();
            addressResponseList.add(new AddressResponse(unrestrictedValue, country, city, postalCode));
        }
        return addressResponseList;
    }
}
