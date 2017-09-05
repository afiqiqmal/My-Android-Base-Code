package com.hackhaton.androidbase.helper;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : hafiq on 07/02/2017.
 */

public enum Language {
    MALAY("ms"),
    ENGLISH("en");

    private final String s;
    private static final Map<String, Language> lookup = new HashMap<>();


    static {
        for (Language s : EnumSet.allOf(Language.class)) {
            lookup.put(s.getName(), s);
        }
    }

    Language(String s){
        this.s = s;
    }

    public String getName() {
        return s;
    }

    public static Language toLanguage (String str) {
        return lookup.get(str);
    }
}
