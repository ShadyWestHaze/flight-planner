package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FlightInMemoryRepository {

    private final Map<Long, Flight> flights = new HashMap<>();
    private Long nextId = 1L;

    public synchronized Flight findById(Long id) {
        return flights.get(id);
    }

    public synchronized Flight save(Flight flight) {
        Long id = getNextId();
        flight.setId(id);
        flights.put(id, flight);
        return flight;
    }

    public List<Flight> findAll() {
        return new ArrayList<>(flights.values());
    }

    public synchronized void deleteById(Long id) {
        flights.remove(id);
    }

    public synchronized Long getNextId() {
        return nextId++;
    }

    public synchronized void clearAll() {
        flights.clear();
        nextId = 1L;
    }
}
