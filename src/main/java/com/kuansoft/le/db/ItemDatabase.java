package com.kuansoft.le.db;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kuansoft.le.equipment.EquipmentBaseType;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDatabase extends HashMap<String, EquipmentBaseType> {

}
