package org.hubson404.carrentalapp.controllers;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.CarReturnDto;
import org.hubson404.carrentalapp.services.CarReturnService;
import org.hubson404.carrentalapp.wrappers.CarReturnCreateWrapper;
import org.hubson404.carrentalapp.wrappers.CarReturnWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CarReturnController {

    private final CarReturnService carReturnService;

    @PostMapping("/returns")
    @ResponseStatus(HttpStatus.CREATED)
    public CarReturnDto createCarReturn(@Valid @RequestBody CarReturnCreateWrapper wrapper) {
        return carReturnService.createCarReturn(wrapper);
    }

    @GetMapping("/returns/{id}")
    public CarReturnDto findById(@PathVariable Long id) {
        return carReturnService.findById(id);
    }

    @GetMapping("/returns")
    public CarReturnWrapper findAll() {
        return carReturnService.findAll();
    }
}
