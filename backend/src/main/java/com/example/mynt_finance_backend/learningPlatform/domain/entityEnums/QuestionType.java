package com.example.mynt_finance_backend.learningPlatform.domain.entityEnums;

public enum QuestionType {

    // Had to make the implementation more complex to work around the issue of annotation attributes needing constant values

    TRUE_FALSE(Constants.TRUE_FALSE_VALUE), MULTIPLE_CHOICE(Constants.MULTIPLE_CHOICE_VALUE);

    QuestionType(String typeString) {
        if (!typeString.equals(this.name())) {
            throw new IllegalArgumentException();
        }
    }

    public static class Constants {
        public static final String TRUE_FALSE_VALUE = "TRUE_FALSE";
        public static final String MULTIPLE_CHOICE_VALUE = "MULTIPLE_CHOICE";
    }
}
