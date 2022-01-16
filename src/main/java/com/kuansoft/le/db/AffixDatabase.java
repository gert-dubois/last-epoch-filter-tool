package com.kuansoft.le.db;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuansoft.le.affix.Affix;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AffixDatabase {

    @JsonProperty("singleAffixes")
    private Map<String, Affix> singleAffixes;

    @JsonProperty("multiAffixes")
    private Map<String, Affix> multiAffixes;

    public Map<String, Affix> getSingleAffixes() {
        return singleAffixes;
    }

    public Map<String, Affix> getMultiAffixes() {
        return multiAffixes;
    }
}
