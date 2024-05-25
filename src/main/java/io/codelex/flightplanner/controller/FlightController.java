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

    @GetMapping("/admin-api/flights/{flightId}")
    public ResponseEntity<?> getFlightDetails(@PathVariable("flightId") int flightId) {
        Flight flight = flightService.getFlightById(flightId);
        if (flight != null) {
            return ResponseEntity.ok(flight);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/flights/{flightID}")
    public ResponseEntity<?> getFlightDetailsForApi(@PathVariable("flightID") int flightID) {
        return getFlightDetails(flightID);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/admin-api/flights")
    public Flight addFlight(@Valid @RequestBody Flight flight) {

        flightService.addFlight(flight);
        return flight;
    }


    @DeleteMapping("/admin-api/flights/{flightId}")
    public ResponseEntity<Void> deleteFlight(@PathVariable("flightId") int flightId) {
        flightService.deleteFlight(flightId);

            return ResponseEntity.ok().build();

    }

    @PostMapping("/testing-api/clear")
    public ResponseEntity<Void> clearFlights() {
        flightService.clearFlights();
        return ResponseEntity.ok().build();
    }
    @PostMapping("/api/flights/search")
    public ResponseEntity<PageResult<Flight>> searchFlights(@Valid @RequestBody SearchFlightsRequest request) {
        if (request.getFrom() == null || request.getTo() == null || request.getDepartureDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request parameters");
        }

        List<Flight> flights = flightService.searchFlights(request);
        PageResult<Flight> result = new PageResult<>(0, flights.size(), flights);
        return ResponseEntity.ok(result);
    }
}//comment for pull
