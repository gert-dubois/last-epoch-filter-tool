package com.kuansoft.le.equipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuansoft.le.game.HasDisplayName;

import java.util.Objects;

public class EquipmentImplicit implements HasDisplayName {

    public enum Type implements HasDisplayName {
        ADDED("+"),
        INCREASED("%"),
        REDUCED("-");

        private final String displayName;

        Type(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String getDisplayName() {
            return displayName;
        }
    }
    @JsonProperty("property")
    private String property;

    @JsonProperty("type")
    private Type type;

    @Override
    public String getDisplayName() {
        return type.getDisplayName() + property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquipmentImplicit that = (EquipmentImplicit) o;

        return Objects.equals(property, that.property);
    }

    @Override
    public int hashCode() {
        return property != null ? property.hashCode() : 0;
    }
}
