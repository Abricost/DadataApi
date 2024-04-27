package ru.example.DataEntryService.model.fio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.DataEntryService.model.fio.enums.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameResponse {
    private String fullName;
    private Gender gender;

}
