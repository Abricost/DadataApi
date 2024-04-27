package ru.example.DataEntryService.model.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private String fullAddress;
    private String country;
    private String city;
    private String postalCode;

}
