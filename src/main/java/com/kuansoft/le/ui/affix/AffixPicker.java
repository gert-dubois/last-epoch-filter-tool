package com.kuansoft.le.ui.affix;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.kuansoft.le.affix.Affix;
import com.kuansoft.le.game.Tag;
import com.kuansoft.le.ui.common.AccordionSelector;
import com.kuansoft.le.ui.common.PanelSelector;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

public class AffixPicker extends Composite<AccordionSelector<Affix>> implements HasSize {

    public AffixPicker(Set<Affix> affixes) {
        Multimap<String, Affix> affixMap = HashMultimap.create();
        for (Affix affix : affixes) {
            if(affix.getClassRequirements().isEmpty()) {
                affixMap.put("General", affix);
            }
            affix.getClassRequirements().forEach(playerClass -> affixMap.put(playerClass.getDisplayName(), affix));
        }
        affixMap.keySet().stream()
                .sorted(Comparator.comparing((String key) -> key.equals("General") ? -1 : 1).thenComparing(Comparator.naturalOrder()))
                .forEach(playerClass -> {
                    PanelSelector<Affix> panel = new PanelSelector<>(playerClass, createAffixAccordion(affixMap.get(playerClass)));
                    getContent().add(panel);
                });
        getContent().setHeightFull();
    }

    private AccordionSelector<Affix> createAffixAccordion(Collection<Affix> affixes) {
        AccordionSelector<Affix> accordion = new AccordionSelector<>();
        Multimap<Tag, Affix> affixMap = HashMultimap.create();
        for (Affix affix : affixes) {
            affix.getTags().forEach(tag -> affixMap.put(tag, affix));
        }
        affixMap.keySet().stream()
                .sorted(Comparator.comparing((Tag tag) -> tag == Tag.MISCELLANEOUS ? -1 : 1)
                        .thenComparing(Tag::getDisplayName))
                .forEach(tag -> {
                    AffixGridSelector grid = new AffixGridSelector();
                    grid.setItems(affixMap.get(tag));
                    PanelSelector<Affix> panel = new PanelSelector<>(tag.getDisplayName(), grid);
                    accordion.add(panel);
                });
        return accordion;
    }

    public Set<Affix> getSelectedItems() {
        return getContent().getSelectedItems();
    }

    public void onSelectionChanged(Runnable onSelectionChanged) {
        getContent().onSelectionChanged(i -> onSelectionChanged.run(), i -> onSelectionChanged.run());
    }
}
