package com.kuansoft.le.ui.equipment;

import com.google.common.collect.Sets;
import com.kuansoft.le.equipment.EquipmentBaseType;
import com.kuansoft.le.equipment.EquipmentSubType;
import com.kuansoft.le.ui.common.AccordionSelector;
import com.kuansoft.le.ui.common.PanelSelector;
import com.kuansoft.le.ui.common.Selector;
import com.vaadin.flow.component.Composite;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EquipmentPicker extends Composite<AccordionSelector<EquipmentSubType>> {

    private final Map<EquipmentBaseType, Selector<EquipmentSubType>> selectors;

    public EquipmentPicker() {
        this.selectors = new HashMap<>();
    }

    public void setEquipmentTypes(Set<EquipmentBaseType> equipmentTypes) {
        this.selectors.clear();
        getContent().clear();
        getContent().add(createWeaponSelectors(equipmentTypes));
        getContent().add(createArmorSelectors(equipmentTypes));
        getContent().add(createIdolSelectors(equipmentTypes));
    }

    private PanelSelector<EquipmentSubType> createIdolSelectors(Set<EquipmentBaseType> equipmentTypes) {
        AccordionSelector<EquipmentSubType> accordion = createSelectorAccordion(equipmentTypes.stream().filter(EquipmentBaseType::isIdol));
        return new PanelSelector<>("Idols", accordion);
    }

    private PanelSelector<EquipmentSubType> createArmorSelectors(Set<EquipmentBaseType> equipmentTypes) {
        AccordionSelector<EquipmentSubType> accordion = createSelectorAccordion(equipmentTypes.stream().filter(EquipmentBaseType::isArmor));
        return new PanelSelector<>("Armor", accordion);
    }

    private PanelSelector<EquipmentSubType> createWeaponSelectors(Set<EquipmentBaseType> equipmentTypes) {
        AccordionSelector<EquipmentSubType> accordion = createSelectorAccordion(equipmentTypes.stream().filter(EquipmentBaseType::isWeapon));
        return new PanelSelector<>("Weapons", accordion);
    }

    private AccordionSelector<EquipmentSubType> createSelectorAccordion(Stream<EquipmentBaseType> equipmentTypeStream) {
        AccordionSelector<EquipmentSubType> container = new AccordionSelector<>();
        equipmentTypeStream
                .sorted(Comparator.comparing(EquipmentBaseType::getDisplayName))
                .forEach(type -> {
                    PanelSelector<EquipmentSubType> selector = createSelector(type);
                    selectors.put(type, selector);
                    container.add(selector);
                });
        return container;
    }

    private PanelSelector<EquipmentSubType> createSelector(EquipmentBaseType equipmentType) {
        EquipmentGridSelector equipmentGrid = new EquipmentGridSelector(equipmentType.getCommonImplicits());
        equipmentGrid.setItems(equipmentType.getSubTypes());
        return new PanelSelector<>(equipmentType.getDisplayName(), equipmentGrid);
    }

    public Set<EquipmentBaseType> getAllSuppressedTypes() {
        return this.selectors.entrySet().stream()
                .filter(entry -> entry.getValue().getSelectedItems().isEmpty())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public Set<EquipmentBaseType> getPartiallySuppressedTypes() {
        return this.selectors.entrySet().stream()
                .filter(entry -> !entry.getValue().getSelectedItems().isEmpty() && !entry.getValue().areAllItemsSelected())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public Set<EquipmentSubType> getSuppressedSubTypes(EquipmentBaseType type) {
        Selector<EquipmentSubType> selector = this.selectors.get(type);
        return Sets.difference(selector.getItems(), selector.getSelectedItems());
    }
}
