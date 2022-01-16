package com.kuansoft.le.equipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kuansoft.le.game.HasDisplayName;
import com.kuansoft.le.game.PlayerClass;
import com.kuansoft.le.game.PlayerClassDeserializer;

import java.util.Set;

public class EquipmentSubType implements HasDisplayName {

    @JsonProperty("subTypeId")
    private int id;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("classRequirement")
    @JsonDeserialize(using = PlayerClassDeserializer.class)
    private Set<PlayerClass> classRequirements;

    @JsonProperty("implicits")
    private Set<EquipmentImplicit> implicits;

    public int getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public Set<PlayerClass> getClassRequirements() {
        return classRequirements;
    }

    public Set<EquipmentImplicit> getImplicits() {
        return implicits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquipmentSubType that = (EquipmentSubType) o;

        if (id != that.id) return false;
        return displayName != null ? displayName.equals(that.displayName) : that.displayName == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        return result;
    }
}
