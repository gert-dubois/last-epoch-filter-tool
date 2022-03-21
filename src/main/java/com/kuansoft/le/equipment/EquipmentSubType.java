package com.kuansoft.le.equipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuansoft.le.game.HasDisplayName;
import com.kuansoft.le.game.HasId;
import com.kuansoft.le.game.PlayerClass;

import java.util.Objects;
import java.util.Set;

public class EquipmentSubType implements HasDisplayName, HasId {

    @JsonProperty("id")
    private int id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty("classRequirement")
    private Set<PlayerClass> classRequirements;

    @JsonProperty("implicits")
    private Set<EquipmentImplicit> implicits;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return name;
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
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
