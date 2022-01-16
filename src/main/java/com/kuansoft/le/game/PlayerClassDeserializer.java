package com.kuansoft.le.game;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.helger.commons.lang.BitSetHelper;

import java.io.IOException;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class PlayerClassDeserializer extends JsonDeserializer<Set<PlayerClass>> {
    @Override
    public Set<PlayerClass> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String currentName = jsonParser.getCurrentName();
        int offset = currentName.equals("classRequirement") ? 0 : 1;
        int classBitset = jsonParser.getIntValue();
        if(classBitset == 0) {
            return Set.of();
        }
        BitSet bitSet = BitSetHelper.createBitSet(classBitset);
        Set<PlayerClass> classList = new HashSet<>();
        if(offset == 1 && bitSet.get(0)) {
            return Set.of();
        } else {
            if(bitSet.get(offset)) {
                classList.add(PlayerClass.PRIMALIST);
            }
            if(bitSet.get(1+offset)) {
                classList.add(PlayerClass.MAGE);
            }
            if(bitSet.get(2+offset)) {
                classList.add(PlayerClass.SENTINEL);
            }
            if(bitSet.get(3+offset)) {
                classList.add(PlayerClass.ACOLYTE);
            }
            if(bitSet.get(4+offset)) {
                classList.add(PlayerClass.ROGUE);
            }
        }
        return classList;
    }
}
