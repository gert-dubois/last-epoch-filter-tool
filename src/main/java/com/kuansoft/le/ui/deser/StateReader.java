package com.kuansoft.le.ui.deser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuansoft.le.affix.AffixRepository;
import com.kuansoft.le.equipment.EquipmentBaseType;
import com.kuansoft.le.equipment.EquipmentTypeRepository;
import com.kuansoft.le.game.PlayerClass;
import com.kuansoft.le.ui.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class StateReader {

    private final ObjectMapper objectMapper;
    private final AffixRepository affixRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;

    @Autowired
    public StateReader(ObjectMapper objectMapper, AffixRepository affixRepository, EquipmentTypeRepository equipmentTypeRepository) {
        this.objectMapper = objectMapper;
        this.affixRepository = affixRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
    }

    public void readState(InputStream inputStream, MainView mainView) {
        try {
            MainViewState mainViewState = objectMapper.readValue(inputStream, MainViewState.class);
            restoreSelectedClasses(mainView, mainViewState.getSelectedClasses());
            restoreSelectedEquipmentAffixes(mainView, mainViewState.getEquipmentAffixes());
            restoreSelectedIdolAffixes(mainView, mainViewState.getIdolAffixes());
            restoreSelectedShatterAffixes(mainView, mainViewState.getShatterAffixes());
            restoreSelectedEquipment(mainView, mainViewState.getSelectedEquipment());
        } catch (IOException ignored) {}
    }

    private void restoreSelectedEquipment(MainView mainView, Map<Integer, Set<Integer>> selectedEquipment) {
        List<EquipmentBaseType> equipmentTypes = equipmentTypeRepository.getEquipmentTypes();
        for (EquipmentBaseType equipmentType : equipmentTypes) {
            Set<Integer> selectedSubTypes = selectedEquipment.getOrDefault(equipmentType.getId(), Set.of());
            equipmentType.getSubTypes().stream()
                    .filter(subType -> selectedSubTypes.contains(subType.getId()))
                    .forEach(subType -> mainView.getEquipmentPicker().select(subType));
        }
    }

    private void restoreSelectedShatterAffixes(MainView mainView, Set<Integer> shatterAffixes) {
        affixRepository.getAffixes().stream()
                .filter(affix -> shatterAffixes.contains(affix.getId()))
                .forEach(affix -> mainView.getEquipmentAffixPicker().select(affix));
    }

    private void restoreSelectedIdolAffixes(MainView mainView, Set<Integer> idolAffixes) {
        affixRepository.getAffixes().stream()
                .filter(affix -> idolAffixes.contains(affix.getId()))
                .forEach(affix -> mainView.getEquipmentAffixPicker().select(affix));
    }

    private void restoreSelectedEquipmentAffixes(MainView mainView, Set<Integer> equipmentAffixes) {
        affixRepository.getAffixes().stream()
                .filter(affix -> equipmentAffixes.contains(affix.getId()))
                .forEach(affix -> mainView.getEquipmentAffixPicker().select(affix));
    }

    private void restoreSelectedClasses(MainView mainView, Set<PlayerClass> selectedClasses) {
        for (PlayerClass selectedClass : selectedClasses) {
            mainView.getClassPicker().select(selectedClass);
        }
    }
}
