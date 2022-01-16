package com.kuansoft.le.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuansoft.le.equipment.EquipmentBaseType;
import com.kuansoft.le.equipment.EquipmentSubType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SubTypeLootFilterCondition extends LootFilterCondition {

    private static final List<Integer> ALL_ITEMS = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 25, 26, 27, 28, 29, 30, 31, 32, 33);

    private static final List<Integer> ALL_IDOLS = List.of(25, 26, 27, 28, 29, 30, 31, 32, 33);

    @JsonProperty
    private List<Integer> types = new ArrayList<>();

    @JsonProperty
    private List<Integer> subTypes = new ArrayList<>();

    private SubTypeLootFilterCondition() {
        super("SubTypeCondition");
    }

    public static SubTypeLootFilterCondition create() {
        return new SubTypeLootFilterCondition();
    }

    public List<Integer> getTypes() {
        return types;
    }


    public SubTypeLootFilterCondition addType(EquipmentBaseType type) {
        this.types.add(type.getId());
        return this;
    }

    public SubTypeLootFilterCondition addTypes(Collection<EquipmentBaseType> types) {
        types.stream().map(EquipmentBaseType::getId).forEach(this.types::add);
        return this;
    }

    public List<Integer> getSubTypes() {
        return subTypes;
    }

    public SubTypeLootFilterCondition addSubTypes(Collection<EquipmentSubType> subTypes) {
        subTypes.stream().map(EquipmentSubType::getId).forEach(this.subTypes::add);
        return this;
    }

    public SubTypeLootFilterCondition allEquipment() {
        this.types.addAll(ALL_ITEMS);
        return this;
    }

    public SubTypeLootFilterCondition allIdols() {
        this.types.addAll(ALL_IDOLS);
        return this;
    }
}
