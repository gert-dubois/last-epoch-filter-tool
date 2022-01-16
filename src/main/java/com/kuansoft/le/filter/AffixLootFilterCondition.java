package com.kuansoft.le.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuansoft.le.affix.Affix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AffixLootFilterCondition extends LootFilterCondition {

    @JsonProperty
    private List<Affix> affixes = new ArrayList<>();

    @JsonProperty
    private String comparison = "ANY";

    @JsonProperty
    private int comparisonValue = 0;

    @JsonProperty
    private int minOnTheSameItem = 1;

    @JsonProperty
    private String combinedComparison = "ANY";

    @JsonProperty
    private int combinedComparisonValue = 1;

    @JsonProperty
    private boolean advanced = false;

    private AffixLootFilterCondition() {
        super("AffixCondition");
    }

    public static AffixLootFilterCondition create() {
        return new AffixLootFilterCondition();
    }

    public String getComparison() {
        return comparison;
    }

    public AffixLootFilterCondition tiersMoreOrEqualTo(int value) {
        this.comparison = "MORE_OR_EQUAL";
        this.comparisonValue = value;
        this.advanced = true;
        return this;
    }

    public int getComparisonValue() {
        return comparisonValue;
    }

    public int getMinOnTheSameItem() {
        return minOnTheSameItem;
    }

    public AffixLootFilterCondition minimumAffixesOnItem(int minOnTheSameItem) {
        this.minOnTheSameItem = minOnTheSameItem;
        return this;
    }

    public String getCombinedComparison() {
        return combinedComparison;
    }

    public AffixLootFilterCondition combinedTiersMoreOrEqualTo(int value) {
        this.combinedComparison = "MORE_OR_EQUAL";
        this.combinedComparisonValue = value;
        this.advanced = true;
        return this;
    }

    public int getCombinedComparisonValue() {
        return combinedComparisonValue;
    }

    public boolean isAdvanced() {
        return advanced;
    }

    public List<Affix> getAffixes() {
        return affixes;
    }

    public AffixLootFilterCondition addAffixes(Collection<Affix> affixes) {
        this.affixes.addAll(affixes);
        return this;
    }
}
