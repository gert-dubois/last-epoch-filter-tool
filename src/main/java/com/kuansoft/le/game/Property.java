package com.kuansoft.le.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Property {

    @JsonProperty("property")
    int id;

    @JsonProperty("propertyName")
    String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
