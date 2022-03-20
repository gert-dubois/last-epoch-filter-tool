package com.kuansoft.le.filter;

import com.google.common.collect.Sets;
import com.kuansoft.le.affix.Affix;
import com.kuansoft.le.equipment.EquipmentBaseType;
import com.kuansoft.le.equipment.EquipmentSubType;
import com.kuansoft.le.game.PlayerClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.kuansoft.le.game.Rarity.*;

public class LootFilterBuilder {

    private String name = "My Loot Filter";
    private String description = "Filter generated using Last Epoch Loot Filter Generator";
    private Set<Affix> targetAffixes;
    private Set<Affix> targetIdolAffixes;
    private Set<Affix> shatterAffixes;
    private Set<EquipmentBaseType> suppressedEquipmentTypes;
    private Map<EquipmentBaseType, Set<EquipmentSubType>> suppressedEquipmentSubTypes = new HashMap<>();
    private Set<PlayerClass> selectedClasses;

    private LootFilterBuilder() {
    }

    public static LootFilterBuilder create() {
        return new LootFilterBuilder();
    }

    public LootFilterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public LootFilterBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public LootFilterBuilder setTargetAffixes(Set<Affix> targetAffixes) {
        this.targetAffixes = targetAffixes;
        return this;
    }

    public LootFilterBuilder setTargetIdolAffixes(Set<Affix> targetIdolAffixes) {
        this.targetIdolAffixes = targetIdolAffixes;
        return this;
    }

    public LootFilterBuilder setShatterAffixes(Set<Affix> shatterAffixes) {
        this.shatterAffixes = shatterAffixes;
        return this;
    }

    public LootFilterBuilder setSuppressedEquipmentTypes(Set<EquipmentBaseType> equipmentTypes) {
        this.suppressedEquipmentTypes = equipmentTypes;
        return this;
    }

    public LootFilterBuilder addSuppressedEquipmentSubTypes(EquipmentBaseType type, Set<EquipmentSubType> subTypes) {
        this.suppressedEquipmentSubTypes.put(type, subTypes);
        return this;
    }

    public LootFilterBuilder setSelectedClasses(Set<PlayerClass> selectedClasses) {
        this.selectedClasses = selectedClasses;
        return this;
    }

    public LootFilter build() {
        LootFilter lootFilter = LootFilter.create()
                .setName(name)
                .setDescription(description);
        addTrashRules(lootFilter);
        addLowLevelRules(lootFilter);
        addStarterItemRules(lootFilter);
        addLateGameItemRules(lootFilter);
        addIdolRules(lootFilter);
        addSuppressionRules(lootFilter);
        addShatterRules(lootFilter);
        addExaltedFilters(lootFilter);
        return lootFilter;
    }

    private void addShatterRules(LootFilter lootFilter) {
        Sets.SetView<Affix> shatterDifference = Sets.difference(shatterAffixes, targetAffixes);
        if(!shatterDifference.isEmpty()) {
            lootFilter.addRule(LootFilterRule.create()
                    .overrideName("items with a shatter affix you are not actively searching for")
                    .setHighlight(14)
                    .setEnabled(false)
                    .makeEmphasized()
                    .addConditions(AffixLootFilterCondition.create()
                            .tiersMoreOrEqualTo(5)
                            .addAffixes(shatterDifference)));
        }

    }

    private void addIdolRules(LootFilter lootFilter) {
        if(!targetIdolAffixes.isEmpty()) {
            lootFilter.addRule(LootFilterRule.create()
                    .setHighlight(15)
                    .addConditions(SubTypeLootFilterCondition.create()
                            .allIdols())
                    .addConditions(AffixLootFilterCondition.create()
                            .addAffixes(targetIdolAffixes)));
        }
    }

    private void addSuppressionRules(LootFilter lootFilter) {
        if(!suppressedEquipmentTypes.isEmpty()) {
            lootFilter.addRule(LootFilterRule.create()
                    .overrideName("all unwanted base items")
                    .setHidden()
                    .makeLevelDependent(10, 100)
                    .addConditions(SubTypeLootFilterCondition.create()
                            .addTypes(suppressedEquipmentTypes))
            );
        }
        for (Map.Entry<EquipmentBaseType, Set<EquipmentSubType>> entry : suppressedEquipmentSubTypes.entrySet()) {
            lootFilter.addRule(LootFilterRule.create().overrideName("all unwanted sub types of " + entry.getKey().getName())
                    .setHidden()
                    .makeLevelDependent(10, 100)
                    .addConditions(SubTypeLootFilterCondition.create()
                            .addType(entry.getKey())
                            .addSubTypes(entry.getValue())));
        }
        Sets.SetView<PlayerClass> suppressedClasses = Sets.difference(Set.of(PlayerClass.values()), selectedClasses);
        if(!suppressedClasses.isEmpty()) {
            lootFilter.addRule(LootFilterRule.create()
                    .overrideName("all items only usable by unwanted classes")
                    .setHidden()
                    .addConditions(ClassLootFilterCondition.create()
                            .addClasses(suppressedClasses)));
        }
    }

    private void addStarterItemRules(LootFilter lootFilter) {
        if(!targetAffixes.isEmpty()) {
            lootFilter.addRule(LootFilterRule.create()
                    .setHighlight(13)
                    .addConditions(AffixLootFilterCondition.create()
                            .addAffixes(targetAffixes)));
            lootFilter.addRule(LootFilterRule.create()
                    .setHighlight(17)
                    .addConditions(AffixLootFilterCondition.create()
                            .tiersMoreOrEqualTo(3)
                            .addAffixes(targetAffixes)));
            lootFilter.addRule(LootFilterRule.create()
                    .setHighlight(3)
                    .addConditions(AffixLootFilterCondition.create()
                            .tiersMoreOrEqualTo(5)
                            .addAffixes(targetAffixes)));
        }
    }

    private void addLateGameItemRules(LootFilter lootFilter) {
        if(!targetAffixes.isEmpty()) {
            lootFilter.addRule(LootFilterRule.create()
                    .setHighlight(5)
                    .addConditions(AffixLootFilterCondition.create()
                            .minimumAffixesOnItem(2)
                            .combinedTiersMoreOrEqualTo(8)
                            .addAffixes(targetAffixes)));
            lootFilter.addRule(LootFilterRule.create()
                    .setHighlight(7)
                    .addConditions(AffixLootFilterCondition.create()
                            .minimumAffixesOnItem(2)
                            .combinedTiersMoreOrEqualTo(12)
                            .addAffixes(targetAffixes)));
        }
    }

    private void addLowLevelRules(LootFilter lootFilter) {
        lootFilter.addRule(LootFilterRule.create()
                .overrideName("all items up to level 10")
                .setHighlight(0)
                .makeLevelDependent(0, 9)
                .addConditions(SubTypeLootFilterCondition.create()
                        .allEquipment()));
    }

    private void addTrashRules(LootFilter lootFilter) {
        lootFilter.addRule(LootFilterRule.create()
                .overrideName("all other items")
                .setHidden()
                .addConditions(RarityLootFilterCondition.create()
                        .addRarities(NORMAL, MAGIC, RARE)));
        Sets.SetView<Affix> shatterIntersection = Sets.intersection(shatterAffixes, targetAffixes);
        if(!shatterIntersection.isEmpty()) {
            lootFilter.addRule(LootFilterRule.create()
                    .overrideName("other items with a shatter affix")
                    .setHighlight(14)
                    .setEnabled(false)
                    .makeEmphasized()
                    .addConditions(AffixLootFilterCondition.create()
                            .tiersMoreOrEqualTo(5)
                            .addAffixes(shatterIntersection)));
        }
    }

    private void addExaltedFilters(LootFilter lootFilter) {
        lootFilter.addRule(LootFilterRule.create()
                .overrideName("all other unique/set/exalted items")
                .setShown()
                .addConditions(RarityLootFilterCondition.create()
                        .addRarities(UNIQUE, SET, EXALTED)));
        if(!targetAffixes.isEmpty()) {
            lootFilter.addRule(LootFilterRule.create()
                    .overrideName("exalted items you can use with >=8 wanted affixes tiers")
                    .setHighlight(5)
                    .makeEmphasized()
                    .addConditions(RarityLootFilterCondition.create()
                            .addRarities(EXALTED))
                    .addConditions(AffixLootFilterCondition.create()
                            .minimumAffixesOnItem(2)
                            .combinedTiersMoreOrEqualTo(8)
                            .addAffixes(targetAffixes)));
            lootFilter.addRule(LootFilterRule.create()
                    .overrideName("exalted items you can use with >=12 wanted affixes tiers")
                    .setHighlight(7)
                    .makeEmphasized()
                    .addConditions(RarityLootFilterCondition.create()
                            .addRarities(EXALTED))
                    .addConditions(AffixLootFilterCondition.create()
                            .minimumAffixesOnItem(2)
                            .combinedTiersMoreOrEqualTo(12)
                            .addAffixes(targetAffixes)));
            lootFilter.addRule(LootFilterRule.create()
                    .overrideName("all exalted items only usable by unwanted classes")
                    .setShown()
                    .addConditions(ClassLootFilterCondition.create()
                            .addClasses(Sets.difference(Set.of(PlayerClass.values()), selectedClasses)))
                    .addConditions(RarityLootFilterCondition.create()
                            .addRarities(EXALTED)));
        }
    }
}
