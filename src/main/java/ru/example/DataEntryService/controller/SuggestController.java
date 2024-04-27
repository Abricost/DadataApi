package ru.example.DataEntryService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.DataEntryService.model.address.AddressResponse;
import ru.example.DataEntryService.model.fio.NameRequest;
import ru.example.DataEntryService.model.fio.NameResponse;
import ru.example.DataEntryService.service.DadataService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SuggestController {

    @Autowired
    private DadataService dadataService;

    @PostMapping("/person/suggest")
    public List<NameResponse> suggestPerson(@RequestBody NameRequest personQuery) throws InterruptedException {
        return dadataService.suggestName(personQuery.getQuery(), personQuery.getCount());
    }

    @GetMapping("/address/suggest")
    public List<AddressResponse> suggestAddress(@RequestParam(name = "query", required = true) String query,
                                                @RequestParam(name = "count", required = false, defaultValue = "10") int count) {
        return dadataService.suggestAddress(query, count);
    }

}
