package io.codelex.flightplanner.service;

import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import io.codelex.flightplanner.repository.FlightDbRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlightDbService implements FlightService {

    private final FlightDbRepository flightDbRepository;
    private final AirportDbService airportDbService;

    public FlightDbService(FlightDbRepository flightDbRepository, AirportDbService airportDbService) {
        this.flightDbRepository = flightDbRepository;
        this.airportDbService = airportDbService;
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightDbRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Flight addFlight(Flight flight) {
        flight.setFromAirport(airportDbService.saveAirportIfNew(flight.getFromAirport()));
        flight.setToAirport(airportDbService.saveAirportIfNew(flight.getToAirport()));

        LocalDateTime departureTime = flight.getDepartureTimeAsDateTime();
        LocalDateTime arrivalTime = flight.getArrivalTimeAsDateTime();
        if (arrivalTime.isBefore(departureTime) || arrivalTime.equals(departureTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arrival time must be after departure time");
        }

        String fromAirportCode = flight.getFromAirport().getAirport().trim();
        String toAirportCode = flight.getToAirport().getAirport().trim();
        if (fromAirportCode.equalsIgnoreCase(toAirportCode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot fly to the same airport");
        }

        Optional<Flight> existingFlight = flightDbRepository.findByFromAirportAndToAirportAndDepartureTimeAndArrivalTimeAndCarrier(flight.getFromAirport(), flight.getToAirport(), flight.getDepartureTime(), flight.getArrivalTime(), flight.getCarrier());
        if (existingFlight.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists");
        }

        return flightDbRepository.save(flight);
    }

    @Override
    public void clearFlights() {
        flightDbRepository.deleteAll();
    }

    @Override
    public void deleteFlight(Long id) {
        flightDbRepository.deleteById(id);
    }

    @Override
    public List<Flight> searchFlights(SearchFlightsRequest request) {
        if (request.getFrom().equalsIgnoreCase(request.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "From and To airports cannot be the same.");
        }
        return flightDbRepository.findAll().stream().filter(flight -> flight.getFromAirport().getAirport().equalsIgnoreCase(request.getFrom())).filter(flight -> flight.getToAirport().getAirport().equalsIgnoreCase(request.getTo())).filter(flight -> flight.getDepartureTime().substring(0, 10).equals(request.getDepartureDate().substring(0, 10))).collect(Collectors.toList());
    }
}
