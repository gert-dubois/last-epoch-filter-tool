package com.kuansoft.le.db;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kuansoft.le.game.Property;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoreDatabase extends ArrayList<Property> {

    public Map<Integer, String> getPropertyMap() {
        return this.stream().collect(Collectors.toMap(Property::getId, Property::getName));
    }

}
