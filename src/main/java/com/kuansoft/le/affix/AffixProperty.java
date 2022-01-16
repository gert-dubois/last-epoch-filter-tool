package com.kuansoft.le.affix;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.helger.commons.lang.BitSetHelper;

import java.util.BitSet;
import java.util.List;

public class AffixProperty {

    public enum Type {
        ADDED("Added", "Reduced"),
        INCREASED("Increased", "Decreased"),
        MORE("More", "Less");

        private final String positive;
        private final String negative;

        Type(String positive, String negative) {
            this.positive = positive;
            this.negative = negative;
        }

        public String getPositive() {
            return positive;
        }

        public String getNegative() {
            return negative;
        }
    }

    @JsonProperty("property")
    @JsonDeserialize(using = PropertyKeyDeserializer.class)
    private String property;

    @JsonProperty("specialTag")
    private int specialTag;

    @JsonProperty("tags")
    private int tags;

    @JsonProperty("modifierType")
    private Type type;

    public String getProperty() {
        return property;
    }

    public boolean hasProperty() {
        return !property.isBlank();
    }

    public boolean isSpecialTag() {
        return specialTag != 0;
    }

    public Type getType() {
        return type;
    }

    public List<Integer> getTags() {
        BitSet bitSet = BitSetHelper.createBitSet(tags);
        return bitSet.stream().map(i -> i+1).boxed().toList();
    }
}
