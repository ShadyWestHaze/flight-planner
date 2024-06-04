package io.codelex.flightplanner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class SearchFlightsRequest {

    @NotNull
    @JsonProperty("from")
    private String from;

    @NotNull
    @JsonProperty("to")
    private String to;

    @NotNull
    @JsonProperty("departureDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String departureDate;

    public @NotNull String getFrom() {
        return from;
    }

    public @NotNull String getTo() {
        return to;
    }

    public @NotNull String getDepartureDate() {
        return departureDate;
    }

}

