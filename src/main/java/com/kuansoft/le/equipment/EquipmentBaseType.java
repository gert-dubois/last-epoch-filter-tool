package com.kuansoft.le.equipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.kuansoft.le.game.HasDisplayName;
import com.kuansoft.le.game.HasId;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EquipmentBaseType implements HasDisplayName, HasId {

    public enum Type {
        ARMOR,
        WEAPON,
        IDOL;
    }
    @JsonProperty("id")
    private int id;

    @JsonProperty("type")
    private Type type;

    @JsonProperty("name")
    private String name;

    @JsonProperty(value = "subTypes")
    private List<EquipmentSubType> subTypes;

    @JsonProperty(value = "size")
    private Map<String, Integer> size;

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        if(getType() == Type.IDOL) {
            return name + " (" + size.get("x") + "x" + size.get("y") + ")";
        }
        return name;
    }

    @JsonIgnore
    public boolean isIdol() {
        return getType() == Type.IDOL;
    }

    @JsonIgnore
    public boolean isWeapon() {
        return getType() == Type.WEAPON;
    }

    @JsonIgnore
    public boolean isArmor() {
        return getType() == Type.ARMOR;
    }

    public Type getType() {
        return type;
    }

    public List<EquipmentSubType> getSubTypes() {
        return subTypes;
    }

    @JsonIgnore
    public Set<EquipmentImplicit> getCommonImplicits() {
        return getSubTypes().stream()
                .map(EquipmentSubType::getImplicits)
                .reduce(Sets::intersection)
                .orElse(Set.of());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquipmentBaseType that = (EquipmentBaseType) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
