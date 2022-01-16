package com.kuansoft.le.equipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.kuansoft.le.game.HasDisplayName;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EquipmentBaseType implements HasDisplayName {

    @JsonProperty("baseTypeId")
    private int id;

    @JsonProperty("baseTypeName")
    private String name;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("isWeapon")
    private boolean weapon;

    @JsonProperty("subItems")
    private Map<String, EquipmentSubType> subItemBases;

    @JsonProperty("size")
    private Map<String, Integer> size;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        if(isIdol()) {
            return displayName + " (" + size.get("x") + "x" + size.get("y") + ")";
        }
        return displayName;
    }

    public boolean isIdol() {
        return name.contains("Idol");
    }

    public boolean isWeapon() {
        return weapon;
    }

    public boolean isArmor() {
        return !isWeapon() && !isIdol();
    }

    public List<EquipmentSubType> getSubTypes() {
        return List.copyOf(subItemBases.values());
    }

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
