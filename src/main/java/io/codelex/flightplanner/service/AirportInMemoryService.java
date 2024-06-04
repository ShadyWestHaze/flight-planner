package io.codelex.flightplanner.service;

import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.repository.FlightInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


public class AirportInMemoryService implements AirportService {

    private final FlightInMemoryRepository flightInMemoryRepository;


    public AirportInMemoryService(FlightInMemoryRepository flightInMemoryRepository) {
        this.flightInMemoryRepository = flightInMemoryRepository;
    }

    public List<Airport> searchAirports(String search) {
        List<Flight> flights = flightInMemoryRepository.findAll();
        List<Airport> matchingAirports = new ArrayList<>();
        String normalizedSearch = search.toLowerCase().trim();

        for (Flight flight : flights) {
            Airport fromAirport = flight.getFromAirport();
            Airport toAirport = flight.getToAirport();

            if (matches(fromAirport, normalizedSearch) && !matchingAirports.contains(fromAirport)) {
                matchingAirports.add(fromAirport);
            }
            if (matches(toAirport, normalizedSearch) && !matchingAirports.contains(toAirport)) {
                matchingAirports.add(toAirport);
            }
        }

        return matchingAirports;
    }

    private boolean matches(Airport airport, String search) {
        return airport.getAirport().toLowerCase().contains(search) ||
                airport.getCity().toLowerCase().contains(search) ||
                airport.getCountry().toLowerCase().contains(search);
    }
}



