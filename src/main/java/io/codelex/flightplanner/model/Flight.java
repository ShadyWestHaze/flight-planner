package io.codelex.flightplanner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_generator")
    @SequenceGenerator(name = "flight_generator", sequenceName = "FLIGHT_SEQ", allocationSize = 1)
    @JsonProperty("id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "from_airport_id")
    @JsonProperty("from")
    private Airport fromAirport;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "to_airport_id")
    @JsonProperty("to")
    private Airport toAirport;

    @NotNull
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String departureTime;

    @NotNull
    @NotBlank
    @JsonProperty("arrivalTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String arrivalTime;

    @NotNull
    @NotBlank
    @JsonProperty("carrier")
    private String carrier;

    public Flight() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartureTime() {
        return departureTime.substring(0, 16);
    }

    public String getArrivalTime() {
        return arrivalTime.substring(0, 16);
    }


    public LocalDateTime getDepartureTimeAsDateTime() {
        return LocalDateTime.parse(departureTime.substring(0, 16), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public LocalDateTime getArrivalTimeAsDateTime() {
        return LocalDateTime.parse(arrivalTime.substring(0, 16), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getCarrier() {
        return carrier;
    }

    public Airport getFromAirport() {
        return fromAirport;
    }

    public Airport getToAirport() {
        return toAirport;
    }

    public void setFromAirport(Airport fromAirport) {
        this.fromAirport = fromAirport;
    }

    public void setToAirport(Airport toAirport) {
        this.toAirport = toAirport;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", fromAirport='" + fromAirport + '\'' +
                ", toAirport='" + toAirport + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", carrier='" + carrier + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight flight)) return false;
        return Objects.equals(getFromAirport(), flight.getFromAirport()) && Objects.equals(getToAirport(), flight.getToAirport()) && Objects.equals(getDepartureTime(), flight.getDepartureTime()) && Objects.equals(getArrivalTime(), flight.getArrivalTime()) && Objects.equals(getCarrier(), flight.getCarrier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFromAirport(), getToAirport(), getDepartureTime(), getArrivalTime(), getCarrier());
    }

}
