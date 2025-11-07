package com.example.mynt_finance_backend.learningPlatformTests.testUtils;

import java.util.function.Consumer;

public class ChildTestUtil {

    public static <T> void addChildren(T child, Consumer<? super T> setter) {
        setter.accept(child);
    }
}
