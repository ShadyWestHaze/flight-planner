package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.service.AirportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/airports")
    public List<Airport> searchAirports(@Valid @RequestParam String search) {
        return airportService.searchAirports(search);
    }
}
