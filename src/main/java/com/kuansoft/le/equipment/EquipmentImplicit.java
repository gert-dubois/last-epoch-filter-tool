package com.kuansoft.le.equipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kuansoft.le.affix.PropertyKeyDeserializer;

public class EquipmentImplicit {

    public enum Type {
        ADDED("+"),
        INCREASED("%"),
        REDUCED("-"),
        ;

        private String displayValue;

        Type(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    @JsonProperty("property")
    @JsonDeserialize(using = PropertyKeyDeserializer.class)
    private String property;

    @JsonProperty("type")
    private Type type;

    public String getProperty() {
        return type.getDisplayValue()+property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquipmentImplicit that = (EquipmentImplicit) o;

        return property != null ? property.equals(that.property) : that.property == null;
    }

    @Override
    public int hashCode() {
        return property != null ? property.hashCode() : 0;
    }
}
