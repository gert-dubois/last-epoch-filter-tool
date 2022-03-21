package com.kuansoft.le.ui.common;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.shared.Registration;

import java.util.function.Supplier;

public class ClipboardButton extends Button {

    private Supplier<String> clipboardContentSupplier;

    public ClipboardButton(Supplier<String> clipboardContentSupplier) {
        super("To Clipboard", VaadinIcon.COPY_O.create());
        this.clipboardContentSupplier = clipboardContentSupplier;
        init();
    }

    private void init() {
        addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addThemeVariants(ButtonVariant.LUMO_SMALL);
        addClickListener(event -> {
            String filter = clipboardContentSupplier.get();
            getElement().executeJs("window.copyToClipboard($0);", filter)
                    .then(r -> fireEvent(new SuccessEvent(this)));
        });
    }

    public Registration addCopySuccessHandler(ComponentEventListener<SuccessEvent> listener) {
        return addListener(SuccessEvent.class, listener);
    }

    public static class SuccessEvent extends ComponentEvent<ClipboardButton> {
        public SuccessEvent(ClipboardButton source) {
            super(source, false);
        }
    }
}
