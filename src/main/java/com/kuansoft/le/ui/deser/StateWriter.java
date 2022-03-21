package com.kuansoft.le.ui.deser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuansoft.le.equipment.EquipmentBaseType;
import com.kuansoft.le.equipment.EquipmentSubType;
import com.kuansoft.le.game.HasId;
import com.kuansoft.le.ui.MainView;
import com.kuansoft.le.ui.equipment.EquipmentPicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StateWriter {

    private final ObjectMapper objectMapper;

    @Autowired
    public StateWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String write(MainView view) {
        try {
            var selectedClasses = view.getClassPicker().getSelectedItems();
            var equipmentAffixes = extractIds(view.getEquipmentAffixPicker().getSelectedItems());
            var idolAffixes = extractIds(view.getIdolsAffixPicker().getSelectedItems());
            var shatterAffixes = extractIds(view.getShatterAffixPicker().getSelectedItems());
            var selectedEquipment = extractEquipmentSelection(view.getEquipmentPicker());
            MainViewState state = new MainViewState(MainView.GAME_VERSION,
                    selectedClasses, equipmentAffixes, idolAffixes, shatterAffixes, selectedEquipment);
            return objectMapper.writeValueAsString(state);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    private Map<Integer, Set<Integer>> extractEquipmentSelection(EquipmentPicker picker) {
        Map<Integer, Set<Integer>> equipmentSelection = new HashMap<>();
        Set<EquipmentBaseType> baseTypes = picker.getBaseTypes();
        for (EquipmentBaseType baseType : baseTypes) {
            Set<Integer> subTypes = picker.getSelectedSubTypes(baseType).stream()
                    .map(EquipmentSubType::getId)
                    .collect(Collectors.toSet());
            if(!subTypes.isEmpty()) {
                equipmentSelection.put(baseType.getId(), subTypes);
            }
        }
        return equipmentSelection;
    }

    private Set<Integer> extractIds(Set<? extends HasId> shatterAffixes) {
        return shatterAffixes.stream()
                .map(HasId::getId)
                .collect(Collectors.toSet());
    }

    private Map<Integer, Set<Integer>> extractIds(Map<EquipmentBaseType, Set<EquipmentSubType>> selectedEquipment) {
        Map<Integer, Set<Integer>> result = new HashMap<>();
        for (Map.Entry<EquipmentBaseType, Set<EquipmentSubType>> entry : selectedEquipment.entrySet()) {
            int baseId = entry.getKey().getId();
            Set<Integer> subIds = extractIds(entry.getValue());
            result.put(baseId, subIds);
        }
        return result;
    }
}
