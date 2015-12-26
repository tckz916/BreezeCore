package com.github.tckz916.breezecore.type;

/**
 * Created by tckz916 on 2015/10/11.
 */
public enum  RotationType {

    Blitz("Blitz"),
    CTW("CTW"),
    Chatora_2("Chatora%202"),
    Chatora("Chatora"),
    Hachiware("Hachiware"),
    Paintball("Paintball"),
    Sabatora_2("Sabatora%202"),
    Sabatora("Sabatora"),
    Splatt_1("Splatt%201"),
    Splatt_2("Splatt%202"),
    Splatt("Splatt"),
    TNT("TNT"),
    Walls1("Walls1");


    private final String type;

    RotationType(String type) {
        this.type = type;
    }

    public String getString() {
        return type;
    }
}
