package com.kuansoft.le.ui.equipment;

import com.kuansoft.le.equipment.EquipmentImplicit;
import com.kuansoft.le.equipment.EquipmentSubType;
import com.kuansoft.le.ui.common.GridSelector;
import com.kuansoft.le.ui.common.TextWithClassRenderer;
import com.kuansoft.le.ui.game.PlayerClassBadge;
import com.vaadin.flow.component.dependency.CssImport;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@CssImport("./themes/lastepochfiltergenerator/components/equipment-grid.css")
public class EquipmentGridSelector extends GridSelector<EquipmentSubType> {

    private final Set<EquipmentImplicit> suppressedImplicits;

    public EquipmentGridSelector(Set<EquipmentImplicit> suppressedImplicits) {
        this.suppressedImplicits = suppressedImplicits;
        addClassName("equipment-grid");
    }

    @Override
    public void setItems(Collection<EquipmentSubType> items) {
        super.setItems(items);
        getContent().addColumn(new TextWithClassRenderer<>((subType) -> subType.getImplicits().stream()
                .filter(implicit -> !suppressedImplicits.contains(implicit))
                .map(EquipmentImplicit::getDisplayName)
                .collect(Collectors.joining(" and ")), "implicits"))
                .setAutoWidth(true)
                .setFlexGrow(0);
        getContent().addComponentColumn(equipmentSubType -> PlayerClassBadge.createBadgeContainer(equipmentSubType.getClassRequirements()));
        getNameColumn().setAutoWidth(true)
                .setFlexGrow(0);
    }
}
