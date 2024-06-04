package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.PageResult;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import io.codelex.flightplanner.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
public class FlightController {

    private final FlightService flightService;
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin-api/flights/{flightId}")
    public Flight getFlightDetails(@PathVariable("flightId") Long flightId) {
        Flight flight = flightService.getFlightById(flightId);
        if (flight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return flight;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/flights/{flightID}")
    public Flight getFlightDetailsForApi(@PathVariable("flightID") Long flightID) {
        return getFlightDetails(flightID);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/admin-api/flights")
    public Flight addFlight(@Valid @RequestBody Flight flight) {

        flightService.addFlight(flight);
        return flight;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/admin-api/flights/{flightId}")
    public void deleteFlight(@PathVariable("flightId") Long flightId) {
        flightService.deleteFlight(flightId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/testing-api/clear")
    public void clearFlights() {
        flightService.clearFlights();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/flights/search")
    public PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightsRequest request) {
        if (request.getFrom() == null || request.getTo() == null || request.getDepartureDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request parameters");
        }

        List<Flight> flights = flightService.searchFlights(request);
        return new PageResult<>(0, flights.size(), flights);
    }
}
