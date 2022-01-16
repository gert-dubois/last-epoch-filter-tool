package com.kuansoft.le.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuansoft.le.game.Rarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RarityLootFilterCondition extends LootFilterCondition {

    @JsonProperty
    private List<String> rarities = new ArrayList<>();

    private RarityLootFilterCondition() {
        super("RarityCondition");
    }

    public static RarityLootFilterCondition create() {
        return new RarityLootFilterCondition();
    }

    public List<String> getRarities() {
        return rarities;
    }

    public RarityLootFilterCondition addRarities(Rarity... rarities) {
        this.rarities.addAll(Arrays.stream(rarities).map(Rarity::name).toList());
        return this;
    }
}
