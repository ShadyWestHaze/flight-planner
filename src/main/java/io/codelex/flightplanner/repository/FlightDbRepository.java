package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightDbRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFromAirportAndToAirportAndDepartureTimeAndArrivalTimeAndCarrier(Airport fromAirport, Airport toAirport, String departureTime, String arrivalTime, String carrier);
}
