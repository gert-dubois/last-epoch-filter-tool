package com.kuansoft.le.ui.common;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;

public class FileDownloadButton extends Anchor {

    private final String fileName;

    public FileDownloadButton(String fileName) {
        this.fileName = fileName;
        getElement().setAttribute("download", true);
        Button button = new Button("Save");
        add(button);
    }

    public void setResource(InputStreamFactory factory) {
        setHref(new StreamResource(fileName, factory));
    }
}
