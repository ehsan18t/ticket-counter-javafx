package dev.pages.ehsan.classes;

import dev.pages.ehsan.classes.Bus;

public class KeyValuePair {
    private final Bus key;
    private final String value;

    public KeyValuePair(Bus key, String value) {
        this.key = key;
        this.value = value;
    }

    public Bus getKey() {
        return key;
    }

    public String toString() {
        return value;
    }
}