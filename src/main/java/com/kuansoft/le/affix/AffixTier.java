package com.kuansoft.le.affix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AffixTier {

    @JsonProperty("rolls")
    List<AffixRoll> rolls;

    public List<AffixRoll> getRolls() {
        return rolls;
    }

    public boolean isNegative() {
        return rolls.stream()
                .allMatch(AffixRoll::isNegative);
    }
}
