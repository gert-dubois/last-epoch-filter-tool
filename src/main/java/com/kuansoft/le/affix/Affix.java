package com.kuansoft.le.affix;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kuansoft.le.game.HasDisplayName;
import com.kuansoft.le.game.PlayerClass;
import com.kuansoft.le.game.PlayerClassDeserializer;
import com.kuansoft.le.game.Tag;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kuansoft.le.affix.AffixProperty.Type.*;
import static com.kuansoft.le.game.Tag.*;

public class Affix implements HasDisplayName {

    public enum Type {
        PREFIX,
        SUFFIX
    }

    @JsonProperty("affixName")
    private String name;

    @JsonProperty("affixDisplayName")
    private String displayName;

    @JsonProperty(value = "affixTitle", defaultValue = "")
    private String title;

    @JsonProperty("affixId")
    private int id;

    @JsonProperty("type")
    private Type type;

    @JsonProperty("isSingle")
    private boolean singlePropertyAffix;

    @JsonProperty("group")
    private int group;

    @JsonProperty("classSpecificity")
    @JsonDeserialize(using = PlayerClassDeserializer.class)
    private Set<PlayerClass> classAllocation;

    @JsonProperty("canRollOn")
    private List<Integer> itemLimitations;

    @JsonProperty("rollsOn")
    private int rollsOnIdol;

    @JsonProperty("affixProperties")
    private List<AffixProperty> affixProperties;

    @JsonProperty("tiers")
    private List<AffixTier> affixTiers;

    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        if(StringUtils.startsWithIgnoreCase(displayName, "increased")) {
            return StringUtils.removeStartIgnoreCase(displayName, "increased").trim();
        }
        if(StringUtils.startsWithIgnoreCase(displayName, "decreased")) {
            return StringUtils.removeStartIgnoreCase(displayName, "decreased").trim();
        }
        if(StringUtils.startsWithIgnoreCase(displayName, "added")) {
            return StringUtils.removeStartIgnoreCase(displayName, "added").trim();
        }
        if(StringUtils.startsWithIgnoreCase(displayName, "reduced")) {
            return StringUtils.removeStartIgnoreCase(displayName, "reduced").trim();
        }
        if(StringUtils.startsWithIgnoreCase(displayName, "more")) {
            return StringUtils.removeStartIgnoreCase(displayName, "more").trim();
        }
        if(StringUtils.startsWithIgnoreCase(displayName, "less")) {
            return StringUtils.removeStartIgnoreCase(displayName, "less").trim();
        }
        return displayName;
    }

    public String getTitle() {
        return Optional.ofNullable(title).orElse("");
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public int getGroup() {
        return group;
    }

    public List<Integer> getItemLimitations() {
        return itemLimitations;
    }

    public int getRollsOnIdol() {
        return rollsOnIdol;
    }

    public String getModifier() {
        AffixProperty.Type modifierType = findModifierType();
        return isNegative() ? modifierType.getNegative() : modifierType.getPositive();
    }

    private AffixProperty.Type findModifierType() {
        if(StringUtils.startsWithIgnoreCase(displayName, "increased")
                || StringUtils.startsWithIgnoreCase(displayName, "decreased")) {
            return INCREASED;
        }
        if(StringUtils.startsWithIgnoreCase(displayName, "added")
                || StringUtils.startsWithIgnoreCase(displayName, "reduced")) {
            return ADDED;
        }
        if(StringUtils.startsWithIgnoreCase(displayName, "more")
                || StringUtils.startsWithIgnoreCase(displayName, "less")) {
            return MORE;
        }
        if(StringUtils.containsIgnoreCase(displayName, "level of")) {
            return ADDED;
        }
        Set<AffixProperty.Type> types = affixProperties.stream()
                .map(AffixProperty::getType)
                .collect(Collectors.toSet());
        if(types.contains(ADDED)) {
            return ADDED;
        }
        if(types.contains(INCREASED)) {
            return INCREASED;
        }
        return MORE;
    }

    public Set<Tag> getTags() {
        Set<Tag> tags = new HashSet<>();
        if(getClassRequirements().isEmpty()) {
            affixProperties.stream()
                    .filter(affixProperty -> !affixProperty.isSpecialTag())
                    .flatMap(affixProperty -> affixProperty.getTags().stream())
                    .distinct()
                    .map(Tag::fromIndex)
                    .forEach(tags::add);
        } else {
            affixProperties.stream()
                    .filter(affixProperty -> affixProperty.hasProperty() && !affixProperty.isSpecialTag())
                    .flatMap(affixProperty -> affixProperty.getTags().stream())
                    .distinct()
                    .map(Tag::fromIndex)
                    .forEach(tags::add);
        }
        if(StringUtils.containsAnyIgnoreCase(displayName, "intelligence", "strength",
                "dexterity", "attunement", "vitality")) {
            tags.add(ATTRIBUTES);
        }
        if(StringUtils.containsAnyIgnoreCase(displayName, "level of")) {
            tags.clear();
            tags.add(SKILL_LEVEL);
        }
        if(StringUtils.containsAnyIgnoreCase(displayName, "freeze", "chill")) {
            tags.add(COLD);
        }
        if(StringUtils.containsAnyIgnoreCase(displayName, "armor")) {
            tags.add(ARMOR);
        }
        for (Tag value : Tag.values()) {
            if(StringUtils.containsAnyIgnoreCase(displayName, value.getDisplayName())) {
                tags.add(value);
            }
        }

        if(tags.isEmpty()) {
            tags.add(Tag.MISCELLANEOUS);
        }
        return tags;
    }

    public boolean isNegative() {
        return affixTiers.stream()
                .allMatch(AffixTier::isNegative);
    }

    public Set<PlayerClass> getClassRequirements() {
        return classAllocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Affix affix = (Affix) o;

        return id == affix.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
