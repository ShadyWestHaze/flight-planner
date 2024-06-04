package io.codelex.flightplanner.service;

import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.repository.AirportDbRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;


public class AirportDbService implements AirportService {

    private final AirportDbRepository airportDbRepository;

    public AirportDbService(AirportDbRepository airportDbRepository) {
        this.airportDbRepository = airportDbRepository;
    }

    public Airport saveAirportIfNew(Airport airport) {
        validateAirportFields(airport);
        Optional<Airport> existingAirport = airportDbRepository.findByCountryAndCityAndAirport(airport.getCountry(), airport.getCity(), airport.getAirport());
        return existingAirport.orElseGet(() -> airportDbRepository.save(airport));
    }

    private void validateAirportFields(Airport airport) {
        if (airport.getAirport() == null || airport.getAirport().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Airport name cannot be null or empty.");
        }
        if (airport.getCountry() == null || airport.getCountry().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country cannot be null or empty.");
        }
        if (airport.getCity() == null || airport.getCity().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City cannot be null or empty.");
        }
    }

    @Override
    public List<Airport> searchAirports(String search) {
        String normalizedSearch = search.toLowerCase().trim();
        return airportDbRepository.searchAirports(normalizedSearch);
    }

}
