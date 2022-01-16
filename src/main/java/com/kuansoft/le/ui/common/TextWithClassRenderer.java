package com.kuansoft.le.ui.common;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.dom.Element;

import java.util.Set;

public class TextWithClassRenderer<T> extends TextRenderer<T> {

    private final Set<String> classes;

    public TextWithClassRenderer(String... classes) {
        super();
        this.classes = Set.of(classes);
    }

    public TextWithClassRenderer(ItemLabelGenerator<T> itemLabelGenerator, String... classes) {
        super(itemLabelGenerator);
        this.classes = Set.of(classes);
    }

    @Override
    protected Element createElement(String item) {
        Element element = super.createElement(item);
        element.getClassList().addAll(classes);
        return element;
    }
}
