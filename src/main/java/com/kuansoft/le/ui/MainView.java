package com.kuansoft.le.ui;

import com.helger.commons.io.stream.StringInputStream;
import com.kuansoft.le.affix.Affix;
import com.kuansoft.le.affix.AffixRepository;
import com.kuansoft.le.equipment.EquipmentBaseType;
import com.kuansoft.le.equipment.EquipmentTypeRepository;
import com.kuansoft.le.filter.LootFilter;
import com.kuansoft.le.filter.LootFilterBuilder;
import com.kuansoft.le.filter.LootFilterWriter;
import com.kuansoft.le.game.PlayerClass;
import com.kuansoft.le.ui.affix.AffixPicker;
import com.kuansoft.le.ui.common.ClipboardButton;
import com.kuansoft.le.ui.common.FileDownloadButton;
import com.kuansoft.le.ui.deser.StateReader;
import com.kuansoft.le.ui.deser.StateWriter;
import com.kuansoft.le.ui.equipment.EquipmentPicker;
import com.kuansoft.le.ui.game.PlayerClassGridSelector;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@Route
@JavaScript("./clipboard-helper.js")
@CssImport("./themes/lastepochfiltergenerator/components/main-view.css")
@CssImport(value = "./themes/lastepochfiltergenerator/components/upload-override.css", themeFor = "vaadin-upload")
public class MainView extends AppLayout implements HasStyle {

    public static final String GAME_VERSION = "0.8.5";
    public static final String HEADER = "Last Epoch Filter Tool";
    public static final String CLIPBOARD_NOTIFICATION = "Filter copied to clipboard";

    private final AffixRepository affixRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;

    private EquipmentPicker equipmentPicker;
    private AffixPicker equipmentAffixPicker;
    private AffixPicker idolsAffixPicker;
    private AffixPicker shatterAffixPicker;
    private PlayerClassGridSelector classPicker;

    private final LootFilterWriter filterWriter;
    private final StateWriter stateWriter;
    private final StateReader stateReader;
    private final VerticalLayout configurationContainer;

    @Autowired
    public MainView(AffixRepository affixRepository,
                    EquipmentTypeRepository equipmentTypeRepository,
                    LootFilterWriter filterWriter,
                    StateWriter stateWriter,
                    StateReader stateReader) {
        this.affixRepository = affixRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.filterWriter = filterWriter;
        this.stateWriter = stateWriter;
        this.stateReader = stateReader;
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
                .filter(affix -> !affix.isRollsOnIdols())
                .collect(Collectors.toSet());
        equipmentAffixPicker = new AffixPicker(equipmentAffixes);
        setupAffixPicker("Equipment Affixes", equipmentAffixPicker);
    }

    private void setupShatterPicker() {
        Set<Affix> affixes = affixRepository.getAffixes().stream()
                .filter(affix -> !affix.isRollsOnIdols())
                .collect(Collectors.toSet());
        shatterAffixPicker = new AffixPicker(affixes);
        setupAffixPicker("Shatter Affixes", shatterAffixPicker);
    }

    private void setupIdolPicker() {
        Set<Affix> idolAffixes = affixRepository.getAffixes().stream()
                .filter(Affix::isRollsOnIdols)
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
        Component header = new H3(HEADER);
        Component version = new H5(GAME_VERSION);

        ClipboardButton copyToClipboardButton = new ClipboardButton(this::generateLootFilter);
        copyToClipboardButton.addCopySuccessHandler(e -> Notification.show(CLIPBOARD_NOTIFICATION, 2000, Notification.Position.TOP_CENTER)
                .addThemeVariants(NotificationVariant.LUMO_PRIMARY));

        FileDownloadButton saveButton = new FileDownloadButton("export.json");
        saveButton.setResource(this::saveState);

        MemoryBuffer buffer = new MemoryBuffer();
        Upload loadButton = new Upload(buffer);
        loadButton.setMaxFiles(1);
        loadButton.setUploadButton(new Button("Load"));
        loadButton.setDropAllowed(false);
        loadButton.setAcceptedFileTypes("application/json");
        loadButton.addAllFinishedListener(event -> {
            InputStream inputStream = buffer.getInputStream();
            loadState(inputStream);
            loadButton.clearFileList();
            Notification.show("Filter preset restored", 2000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        });

        HorizontalLayout navContainer = new HorizontalLayout();
        navContainer.add(header);
        navContainer.addAndExpand(version);
        navContainer.add(copyToClipboardButton);
        navContainer.add(saveButton);
        navContainer.add(loadButton);
        navContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        navContainer.addClassName("navigation");

        addToNavbar(navContainer);
    }

    private void loadState(InputStream inputStream) {
        stateReader.readState(inputStream, this);
    }

    private InputStream saveState() {
        return new StringInputStream(stateWriter.write(this), StandardCharsets.UTF_8);
    }

    private String generateLootFilter() {
        LootFilterBuilder builder = LootFilterBuilder.create()
                .setSelectedClasses(classPicker.getSelectedItems())
                .setTargetAffixes(equipmentAffixPicker.getSelectedItems())
                .setTargetIdolAffixes(idolsAffixPicker.getSelectedItems())
                .setShatterAffixes(shatterAffixPicker.getSelectedItems())
                .setSuppressedEquipmentTypes(equipmentPicker.getNotSelectedBaseTypes());
        for (EquipmentBaseType selectedType : equipmentPicker.getPartiallySelectedBaseTypes()) {
            builder.addSuppressedEquipmentSubTypes(selectedType, equipmentPicker.getNotSelectedSubTypes(selectedType));
        }
        LootFilter filter = builder.build();
        return filterWriter.writeFilter(filter);
    }

    public EquipmentPicker getEquipmentPicker() {
        return equipmentPicker;
    }

    public AffixPicker getEquipmentAffixPicker() {
        return equipmentAffixPicker;
    }

    public AffixPicker getIdolsAffixPicker() {
        return idolsAffixPicker;
    }

    public AffixPicker getShatterAffixPicker() {
        return shatterAffixPicker;
    }

    public PlayerClassGridSelector getClassPicker() {
        return classPicker;
    }
}
