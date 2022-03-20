package com.kuansoft.le.filter;

import com.kuansoft.le.affix.Affix;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LootFilterWriter {

    private final Map<Integer, String> filterEquipmentTypeMapping = new HashMap<>();

    public LootFilterWriter() {
        filterEquipmentTypeMapping.put(0, "HELMET");
        filterEquipmentTypeMapping.put(1, "BODY_ARMOR");
        filterEquipmentTypeMapping.put(2, "BELT");
        filterEquipmentTypeMapping.put(3, "BOOTS");
        filterEquipmentTypeMapping.put(4, "GLOVES");
        filterEquipmentTypeMapping.put(5, "ONE_HANDED_AXE");
        filterEquipmentTypeMapping.put(6, "ONE_HANDED_DAGGER");
        filterEquipmentTypeMapping.put(7, "ONE_HANDED_MACES");
        filterEquipmentTypeMapping.put(8, "ONE_HANDED_SCEPTRE");
        filterEquipmentTypeMapping.put(9, "ONE_HANDED_SWORD");
        filterEquipmentTypeMapping.put(10, "WAND");
        filterEquipmentTypeMapping.put(12, "TWO_HANDED_AXE");
        filterEquipmentTypeMapping.put(13, "TWO_HANDED_MACE");
        filterEquipmentTypeMapping.put(14, "TWO_HANDED_SPEAR");
        filterEquipmentTypeMapping.put(15, "TWO_HANDED_STAFF");
        filterEquipmentTypeMapping.put(16, "TWO_HANDED_SWORD");
        filterEquipmentTypeMapping.put(17, "QUIVER");
        filterEquipmentTypeMapping.put(18, "SHIELD");
        filterEquipmentTypeMapping.put(19, "CATALYST");
        filterEquipmentTypeMapping.put(20, "AMULET");
        filterEquipmentTypeMapping.put(21, "RING");
        filterEquipmentTypeMapping.put(22, "RELIC");
        filterEquipmentTypeMapping.put(23, "BOW");
        filterEquipmentTypeMapping.put(25, "IDOL_1x1_ETERRA");
        filterEquipmentTypeMapping.put(26, "IDOL_1x1_LAGON");
        filterEquipmentTypeMapping.put(27, "IDOL_2x1");
        filterEquipmentTypeMapping.put(28, "IDOL_1x2");
        filterEquipmentTypeMapping.put(29, "IDOL_3x1");
        filterEquipmentTypeMapping.put(30, "IDOL_1x3");
        filterEquipmentTypeMapping.put(31, "IDOL_4x1");
        filterEquipmentTypeMapping.put(32, "IDOL_1x4");
        filterEquipmentTypeMapping.put(33, "IDOL_2x2");
    }

    public String writeFilter(LootFilter lootFilter) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("ItemFilter");
        root.addNamespace("i", "http://www.w3.org/2001/XMLSchema-instance");
        root.addElement("filterIcon").addText("0");
        root.addElement("filterIconColor").addText("0");
        root.addElement("description").addText(lootFilter.getDescription());
        root.addElement("name").addText(lootFilter.getName());
        root.addElement("lastModifiedInVersion").addText("0.8.5");
        root.addElement("lootFilterVersion").addText("2");
        Element rules = root.addElement("rules");
        for (LootFilterRule rule : lootFilter.getRules()) {
            addRule(rules, rule);
        }

        try {
            StringWriter stringWriter = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(stringWriter, OutputFormat.createPrettyPrint());
            xmlWriter.write(document);
            xmlWriter.close();
            return stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void addRule(Element rulesElement, LootFilterRule rule) {
        Element ruleElement = rulesElement.addElement("Rule");
        ruleElement.addElement("type").setText(rule.getType());
        ruleElement.addElement("color").setText(String.valueOf(rule.getColor()));
        ruleElement.addElement("isEnabled").addText(String.valueOf(rule.isEnabled()));
        ruleElement.addElement("levelDependent").addText(String.valueOf(rule.isLevelDependent()));
        ruleElement.addElement("minLvl").addText(String.valueOf(rule.getMinLevel()));
        ruleElement.addElement("maxLvl").addText(String.valueOf(rule.getMaxLevel()));
        ruleElement.addElement("emphasized").addText(String.valueOf(rule.isEmphasized()));
        Element nameOverride = ruleElement.addElement("nameOverride");
        rule.getNameOverride().ifPresent(nameOverride::addText);
        Element conditionsElement = ruleElement.addElement("conditions");
        for (LootFilterCondition condition : rule.getConditions()) {
            addCondition(conditionsElement, condition);
        }
    }

    private void addCondition(Element conditionsElement, LootFilterCondition condition) {
        Element conditionElement = conditionsElement.addElement("Condition");
        conditionElement.addAttribute("i:type", condition.getType());
        if (condition instanceof AffixLootFilterCondition affixLootFilterCondition) {
            Element affixesElement = conditionElement.addElement("affixes");
            for (Affix affix : affixLootFilterCondition.getAffixes()) {
                affixesElement.addElement("int").addText(String.valueOf(affix.getId()));
            }
            conditionElement.addElement("comparsion").setText(affixLootFilterCondition.getComparison());
            conditionElement.addElement("comparsionValue").setText(String.valueOf(affixLootFilterCondition.getComparisonValue()));
            conditionElement.addElement("minOnTheSameItem").setText(String.valueOf(affixLootFilterCondition.getMinOnTheSameItem()));
            conditionElement.addElement("combinedComparsion").setText(affixLootFilterCondition.getCombinedComparison());
            conditionElement.addElement("combinedComparsionValue").setText(String.valueOf(affixLootFilterCondition.getCombinedComparisonValue()));
            conditionElement.addElement("advanced").setText(String.valueOf(affixLootFilterCondition.isAdvanced()));
        } else if (condition instanceof ClassLootFilterCondition classLootFilterCondition) {
            String classes = String.join(" ", classLootFilterCondition.getClasses());
            conditionElement.addElement("req").addText(classes);
        } else if (condition instanceof RarityLootFilterCondition rarityLootFilterCondition) {
            String rarities = String.join(" ", rarityLootFilterCondition.getRarities());
            conditionElement.addElement("rarity").addText(rarities);
        } else if (condition instanceof SubTypeLootFilterCondition subTypeLootFilterCondition) {
            List<String> types = subTypeLootFilterCondition.getTypes().stream()
                    .map(filterEquipmentTypeMapping::get).toList();
            Element typeElement = conditionElement.addElement("type");
            for (String type : types) {
                typeElement.addElement("EquipmentType").addText(type);
            }
            Element subTypesElement = conditionElement.addElement("subTypes");
            for (Integer subType : subTypeLootFilterCondition.getSubTypes()) {
                subTypesElement.addElement("int").addText(String.valueOf(subType));
            }
        }
    }
}
