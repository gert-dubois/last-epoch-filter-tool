package com.kuansoft.le.equipment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuansoft.le.db.ItemDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.Set;

@Component
public class EquipmentTypeRepository {

    private final Set<EquipmentBaseType> itemBases;

    @Autowired
    public EquipmentTypeRepository(ObjectMapper objectMapper) throws IOException {
        ItemDatabase itemDatabase = objectMapper.readValue(ResourceUtils.getURL("classpath:item-db.json"), ItemDatabase.class);
        itemBases = Set.copyOf(itemDatabase.values());
    }

    public Set<EquipmentBaseType> getEquipmentTypes() {
        return itemBases;
    }
}
