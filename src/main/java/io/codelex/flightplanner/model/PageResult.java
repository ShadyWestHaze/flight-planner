package io.codelex.flightplanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PageResult<T> {

    @JsonProperty("page")
    private int page;

    @JsonProperty("totalItems")
    private int totalItems;

    @JsonProperty("items")
    private List<T> items;

    public PageResult(int page, int totalItems, List<T> items) {
        this.page = page;
        this.totalItems = totalItems;
        this.items = items;
    }

}
