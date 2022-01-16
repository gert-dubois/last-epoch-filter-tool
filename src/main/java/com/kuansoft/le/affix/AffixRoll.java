package com.kuansoft.le.affix;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AffixRoll {

    @JsonProperty("min")
    private float min;

    @JsonProperty("max")
    private float max;

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public boolean isNegative() {
        return min < 0 && max < 0;
    }
}
