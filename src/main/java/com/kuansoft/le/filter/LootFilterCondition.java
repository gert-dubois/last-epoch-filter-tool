package com.kuansoft.le.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AffixLootFilterCondition.class, name = "AffixCondition"),
        @JsonSubTypes.Type(value = ClassLootFilterCondition.class, name = "ClassCondition"),
        @JsonSubTypes.Type(value = SubTypeLootFilterCondition.class, name = "SubTypeCondition"),
        @JsonSubTypes.Type(value = RarityLootFilterCondition.class, name = "RarityCondition")
})
public class LootFilterCondition {

    @JsonProperty
    private String type;

    protected LootFilterCondition() {}

    public LootFilterCondition(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
