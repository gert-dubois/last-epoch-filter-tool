package com.kuansoft.le.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class LootFilter {

    private LootFilter() {
    }

    @JsonProperty
    private String name = "";

    @JsonProperty
    private String description = "";

    @JsonProperty
    private final List<LootFilterRule> rules = new ArrayList<>();

    public static LootFilter create() {
        return new LootFilter();
    }

    public String getName() {
        return name;
    }

    public LootFilter setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LootFilter setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<LootFilterRule> getRules() {
        return rules;
    }

    public LootFilter addRule(LootFilterRule rule) {
        this.rules.add(rule);
        return this;
    }
}
