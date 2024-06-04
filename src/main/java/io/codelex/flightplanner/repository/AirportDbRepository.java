package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AirportDbRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByCountryAndCityAndAirport(String country, String city, String airport);

    @Query("SELECT DISTINCT a FROM Airport a " +
            "WHERE LOWER(a.airport) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.city) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.country) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Airport> searchAirports(@Param("search") String search);

}
