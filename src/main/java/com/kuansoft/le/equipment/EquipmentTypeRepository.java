package com.kuansoft.le.equipment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuansoft.le.db.ItemDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.List;

@Component
public class EquipmentTypeRepository {

    private final List<EquipmentBaseType> itemBases;

    @Autowired
    public EquipmentTypeRepository(ObjectMapper objectMapper) throws IOException {
        itemBases = objectMapper.readValue(ResourceUtils.getURL("classpath:item-db.json"), ItemDatabase.class);
    }

    public List<EquipmentBaseType> getEquipmentTypes() {
        return itemBases;
    }
}
