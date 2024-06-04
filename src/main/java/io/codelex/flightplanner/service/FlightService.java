package io.codelex.flightplanner.service;

import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.SearchFlightsRequest;

import java.util.List;

public interface FlightService {
    Flight getFlightById(Long id);
    Flight addFlight(Flight flight) ;
    void clearFlights();
    void deleteFlight(Long id);
    List<Flight> searchFlights(SearchFlightsRequest request);
}
