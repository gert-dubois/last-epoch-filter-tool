package com.kuansoft.le.affix;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kuansoft.le.game.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

public class PropertyKeyDeserializer extends JsonDeserializer<String> {

    @Autowired
    private PropertyRepository propertyRepository;

    public PropertyKeyDeserializer() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        int propertyId = jsonParser.getIntValue();
        return propertyRepository.getProperty(propertyId)
                .orElse("");
    }
}
