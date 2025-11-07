package com.example.mynt_finance_backend.util.baseInterfaces;

import java.util.function.Consumer;

public interface Service<Id> {

    boolean exists(Id id);

    default <T> void updateFieldIfNotNull(Consumer<? super T> consumer, T field) {
        if (field != null) {
            consumer.accept(field);
        }
    }
}
