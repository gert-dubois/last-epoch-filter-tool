package com.kuansoft.le.ui.deser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuansoft.le.game.PlayerClass;

import java.util.Map;
import java.util.Set;

public class MainViewState {

    @JsonProperty("version")
    private String version;

    @JsonProperty("classes")
    private Set<PlayerClass> selectedClasses;

    @JsonProperty("equipmentAffixes")
    private Set<Integer> equipmentAffixes;

    @JsonProperty("idolAffixes")
    private Set<Integer> idolAffixes;

    @JsonProperty("shatterAffixes")
    private Set<Integer> shatterAffixes;

    @JsonProperty("selectedEquipment")
    private Map<Integer, Set<Integer>> selectedEquipment;

    public MainViewState(String version,
                         Set<PlayerClass> selectedClasses,
                         Set<Integer> equipmentAffixes,
                         Set<Integer> idolAffixes,
                         Set<Integer> shatterAffixes,
                         Map<Integer, Set<Integer>> selectedEquipment) {
        this.version = version;

        this.selectedClasses = selectedClasses;
        this.equipmentAffixes = equipmentAffixes;
        this.idolAffixes = idolAffixes;
        this.shatterAffixes = shatterAffixes;
        this.selectedEquipment = selectedEquipment;
    }

    public Set<PlayerClass> getSelectedClasses() {
        return selectedClasses;
    }

    public Set<Integer> getEquipmentAffixes() {
        return equipmentAffixes;
    }

    public Set<Integer> getIdolAffixes() {
        return idolAffixes;
    }

    public Set<Integer> getShatterAffixes() {
        return shatterAffixes;
    }

    public Map<Integer, Set<Integer>> getSelectedEquipment() {
        return selectedEquipment;
    }
}
