package ru.example.DataEntryService.model.address;

import lombok.Data;

@Data
public class AddressRequest {
    private String query;
    private int count;

}
