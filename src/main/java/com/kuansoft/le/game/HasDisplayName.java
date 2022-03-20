package com.kuansoft.le.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface HasDisplayName {

    @JsonIgnore
    String getDisplayName();

}
