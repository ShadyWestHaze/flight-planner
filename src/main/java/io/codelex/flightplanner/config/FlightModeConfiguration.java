package io.codelex.flightplanner.config;

import io.codelex.flightplanner.repository.FlightDbRepository;
import io.codelex.flightplanner.repository.FlightInMemoryRepository;
import io.codelex.flightplanner.service.AirportDbService;
import io.codelex.flightplanner.service.FlightDbService;
import io.codelex.flightplanner.service.FlightService;
import io.codelex.flightplanner.service.FlightServiceInMemory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightModeConfiguration {

    @Bean
    @ConditionalOnProperty(name = "myapp.flight-storage-mode", havingValue = "Database")
    public FlightService flightDbService(FlightDbRepository flightDbRepository, AirportDbService airportDbService) {
        return new FlightDbService(flightDbRepository, airportDbService);
    }

    @Bean
    @ConditionalOnProperty(name = "myapp.flight-storage-mode", havingValue = "InMemory")
    public FlightService flightInMemoryService(FlightInMemoryRepository flightInMemoryRepository) {
        return new FlightServiceInMemory(flightInMemoryRepository);
    }
}
