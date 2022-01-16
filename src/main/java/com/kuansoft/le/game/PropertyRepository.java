package com.kuansoft.le.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuansoft.le.db.CoreDatabase;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class PropertyRepository {

    private final Map<Integer, String> properties;

    public PropertyRepository(ObjectMapper objectMapper) throws IOException {
        CoreDatabase coreDatabase = objectMapper.readValue(ResourceUtils.getURL("classpath:property-db.json"), CoreDatabase.class);
        this.properties = coreDatabase.getPropertyMap();
    }

    public Optional<String> getProperty(int propertyId) {
        if(propertyId == 58) {
            return Optional.empty();
        }
        return Optional.ofNullable(properties.get(propertyId));
    }
}
