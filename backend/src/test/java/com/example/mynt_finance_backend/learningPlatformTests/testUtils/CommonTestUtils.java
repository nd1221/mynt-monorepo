package com.example.mynt_finance_backend.learningPlatformTests.testUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CommonTestUtils {

    public static <T> Set<T> toSet(T... entities) {
        Set<T> entitySet = new HashSet<>();
        Collections.addAll(entitySet, entities);
        return entitySet;
    }

    public static Set<String> getCreators() {
        Set<String> creators = new HashSet<>();
        creators.add("creator1");
        creators.add("creator2");
        return creators;
    }

    public static Set<String> getStringSet() {
        Set<String> stringIds = new HashSet<>();
        stringIds.add("string1");
        stringIds.add("string2");
        stringIds.add("string3");
        return stringIds;
    }

    public static Set<Integer> getIntegerIds() {
        Set<Integer> integerIds = new HashSet<>();
        integerIds.add(1);
        integerIds.add(2);
        integerIds.add(3);
        return integerIds;
    }

    public static Set<Integer> getUpdatedIntegerIds() {
        Set<Integer> integerIds = new HashSet<>();
        integerIds.add(4);
        integerIds.add(5);
        integerIds.add(6);
        return integerIds;
    }

    public static Set<Long> getLongIds() {
        Set<Long> longIds = new HashSet<>();
        longIds.add(1L);
        longIds.add(2L);
        longIds.add(3L);
        return longIds;
    }

    public static Set<Long> getUpdatedLongIds() {
        Set<Long> longIds = new HashSet<>();
        longIds.add(4L);
        longIds.add(5L);
        longIds.add(6L);
        return longIds;
    }
}
