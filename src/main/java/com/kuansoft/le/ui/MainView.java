package com.kuansoft.le.ui;

import com.kuansoft.le.affix.Affix;
import com.kuansoft.le.affix.AffixRepository;
import com.kuansoft.le.equipment.EquipmentBaseType;
import com.kuansoft.le.equipment.EquipmentTypeRepository;
import com.kuansoft.le.filter.LootFilter;
import com.kuansoft.le.filter.LootFilterBuilder;
import com.kuansoft.le.filter.LootFilterWriter;
import com.kuansoft.le.game.PlayerClass;
import com.kuansoft.le.ui.affix.AffixPicker;
import com.kuansoft.le.ui.equipment.EquipmentPicker;
import com.kuansoft.le.ui.game.PlayerClassGridSelector;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Route
@JavaScript("./clipboard-helper.js")
@CssImport("./themes/lastepochfiltergenerator/components/main-view.css")
public class MainView extends AppLayout implements HasStyle {

    private final AffixRepository affixRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;

    private EquipmentPicker equipmentPicker;
    private AffixPicker equipmentAffixPicker;
    private AffixPicker idolsAffixPicker;
    private AffixPicker shatterAffixPicker;
    private PlayerClassGridSelector classPicker;

    private final LootFilterWriter writer;
    private final VerticalLayout configurationContainer;

    @Autowired
    public MainView(AffixRepository affixRepository,
                    EquipmentTypeRepository equipmentTypeRepository,
                    LootFilterWriter writer) {
        this.affixRepository = affixRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.writer = writer;
        this.configurationContainer = new VerticalLayout();
        setupPageHeader();
        setContent(configurationContainer);
        setupConfigurationContainer();
        addClassName("main-view");
    }

    private void setupConfigurationContainer() {
        configurationContainer.setPadding(false);
        configurationContainer.setSpacing(false);
        setupClassPicker();
        setupItemPicker();
        setupEquipmentPicker();
        setupIdolPicker();
        setupShatterPicker();
    }

    private void setupClassPicker() {
        classPicker = new PlayerClassGridSelector();
        classPicker.setItems(Set.of(PlayerClass.values()));
        Details details = new Details("Class", classPicker);
        details.setWidthFull();
        configurationContainer.add(details);
    }

    private void setupItemPicker() {
        equipmentPicker = new EquipmentPicker();
        equipmentPicker.setEquipmentTypes(equipmentTypeRepository.getEquipmentTypes());
        Details details = new Details("Equipment", equipmentPicker);
        details.setWidthFull();
        configurationContainer.add(details);
    }

    private void setupEquipmentPicker() {
        Set<Affix> equipmentAffixes = affixRepository.getAffixes().stream()
                .filter(affix -> affix.getRollsOnIdol() == 0)
                .collect(Collectors.toSet());
        equipmentAffixPicker = new AffixPicker(equipmentAffixes);
        setupAffixPicker("Equipment Affixes", equipmentAffixPicker);
    }

    private void setupShatterPicker() {
        Set<Affix> affixes = affixRepository.getAffixes().stream()
                .filter(affix -> affix.getRollsOnIdol() == 0)
                .collect(Collectors.toSet());
        shatterAffixPicker = new AffixPicker(affixes);
        setupAffixPicker("Shatter Affixes", shatterAffixPicker);
    }

    private void setupIdolPicker() {
        Set<Affix> idolAffixes = affixRepository.getAffixes().stream()
                .filter(affix -> affix.getRollsOnIdol() == 1)
                .collect(Collectors.toSet());
        idolsAffixPicker = new AffixPicker(idolAffixes);
        setupAffixPicker("Idol Affixes", idolsAffixPicker);
    }

    private void setupAffixPicker(String label, AffixPicker picker) {
        Details details = new Details(label, picker);
        details.setWidthFull();
        configurationContainer.add(details);
    }

    private void setupPageHeader() {
        Component header = new H3("Last Epoch Filter Tool");
        Button copyToClipboardButton = new Button("To Clipboard", VaadinIcon.COPY_O.create());
        copyToClipboardButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        copyToClipboardButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        copyToClipboardButton.addClickListener(event -> {
            String filter = generateLootFilter();
            getElement().executeJs("window.copyToClipboard($0);", filter)
                    .then(r -> Notification.show("Filter generated to clipboard", 2000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_PRIMARY));
        });
        HorizontalLayout navContainer = new HorizontalLayout();
        navContainer.addAndExpand(header);
        navContainer.add(copyToClipboardButton);
        navContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        navContainer.addClassName("navigation");
        addToNavbar(navContainer);
    }

    private String generateLootFilter() {
        LootFilterBuilder builder = LootFilterBuilder.create()
                .setSelectedClasses(classPicker.getSelectedItems())
                .setTargetAffixes(equipmentAffixPicker.getSelectedItems())
                .setTargetIdolAffixes(idolsAffixPicker.getSelectedItems())
                .setShatterAffixes(shatterAffixPicker.getSelectedItems())
                .setSuppressedEquipmentTypes(equipmentPicker.getAllSuppressedTypes());
        for (EquipmentBaseType selectedType : equipmentPicker.getPartiallySuppressedTypes()) {
            builder.addSuppressedEquipmentSubTypes(selectedType, equipmentPicker.getSuppressedSubTypes(selectedType));
        }
        LootFilter filter = builder.build();
        return writer.writeFilter(filter);
    }
}
