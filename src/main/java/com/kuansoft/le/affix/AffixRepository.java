package com.kuansoft.le.affix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuansoft.le.db.AffixDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.List;

@Component
public class AffixRepository {

    private final List<Affix> affixes;

    @Autowired
    public AffixRepository(ObjectMapper objectMapper) throws IOException {
        affixes = objectMapper.readValue(ResourceUtils.getURL("classpath:affix-db.json"), AffixDatabase.class);
    }

    public List<Affix> getAffixes() {
        return affixes;
    }
}
