package io.codelex.flightplanner.config;

import io.codelex.flightplanner.repository.AirportDbRepository;
import io.codelex.flightplanner.repository.FlightInMemoryRepository;
import io.codelex.flightplanner.service.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AirportModeConfiguration {

    @Bean
    @ConditionalOnProperty(name = "myapp.airport-storage-mode", havingValue = "Database")
    public AirportService airportDbService(AirportDbRepository airportDbRepository) {
        return new AirportDbService(airportDbRepository);
    }

    @Bean
    @ConditionalOnProperty(name = "myapp.airport-storage-mode", havingValue = "InMemory")
    public AirportService airportInMemoryService(FlightInMemoryRepository flightInMemoryRepository) {
        return new AirportInMemoryService(flightInMemoryRepository);
    }
}

