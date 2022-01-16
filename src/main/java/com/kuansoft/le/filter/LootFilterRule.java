package com.kuansoft.le.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LootFilterRule {

    private LootFilterRule() {
    }

    @JsonProperty
    private String type = "SHOW";

    @JsonProperty
    private int color = 0;

    @JsonProperty
    private boolean enabled = true;

    @JsonProperty
    private boolean levelDependent = false;

    @JsonProperty
    private int minLevel = 0;

    @JsonProperty
    private int maxLevel = 0;

    @JsonProperty
    private boolean emphasized = false;

    @JsonProperty
    private String nameOverride;

    @JsonProperty
    private List<LootFilterCondition> conditions = new ArrayList<>();

    public static LootFilterRule create() {
        return new LootFilterRule();
    }

    public String getType() {
        return type;
    }

    public LootFilterRule setHidden() {
        this.type = "HIDE";
        return this;
    }

    public LootFilterRule setShown() {
        this.type = "SHOW";
        return this;
    }

    public LootFilterRule setHighlight(int color) {
        this.type = "HIGHLIGHT";
        this.color = color;
        return this;
    }

    public int getColor() {
        return color;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public LootFilterRule setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public boolean isLevelDependent() {
        return levelDependent;
    }

    public LootFilterRule makeLevelDependent(int minLevel, int maxLevel) {
        this.levelDependent = true;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        return this;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public boolean isEmphasized() {
        return emphasized;
    }

    public LootFilterRule makeEmphasized() {
        this.emphasized = true;
        return this;
    }

    public Optional<String> getNameOverride() {
        return Optional.ofNullable(nameOverride);
    }

    public LootFilterRule overrideName(String name) {
        this.nameOverride = name;
        return this;
    }

    public List<LootFilterCondition> getConditions() {
        return conditions;
    }

    public LootFilterRule addConditions(LootFilterCondition... conditions) {
        this.conditions.addAll(Arrays.stream(conditions).toList());
        return this;
    }
}
