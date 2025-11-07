package com.example.mynt_finance_backend.learningPlatformTests.testUtils;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.ChoiceDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.ChoiceEntity;

import java.util.Set;

public final class ChoiceTestUtil {

    private ChoiceTestUtil() {}

    public static ChoiceEntity createEmptyChoice() {
        ChoiceEntity choice = new ChoiceEntity();
        choice.setChoiceText("choice");
        choice.setCorrect(true);
        return choice;
    }

    public static Set<ChoiceEntity> createChoiceEntitySetWithIds() {

        ChoiceEntity choice1 = new ChoiceEntity();
        choice1.setId(1);
        ChoiceEntity choice2 = new ChoiceEntity();
        choice1.setId(2);
        ChoiceEntity choice3 = new ChoiceEntity();
        choice1.setId(3);

        return Set.of(
                choice1,
                choice2,
                choice3
        );
    }

    public static Set<ChoiceEntity> createEmptyChoiceSet() {

        ChoiceEntity choice1 = createEmptyChoice();
        ChoiceEntity choice2 = createEmptyChoice();
        choice2.setChoiceText("choice2");
        ChoiceEntity choice3 = createEmptyChoice();
        choice3.setChoiceText("choice3");

        return CommonTestUtils.toSet(
                choice1,
                choice2,
                choice3
        );
    }

    public static Set<ChoiceDTO> createFullChoiceDTOSet() {
        return Set.of(
                new ChoiceDTO(1, "choice1", true),
                new ChoiceDTO(2, "choice2", false),
                new ChoiceDTO(3, "choice3", false)
        );
    }

    public static ChoiceDTO createEmptyChoiceDTO() {
        return new ChoiceDTO(
                null,
                "choice",
                true
        );
    }

    public static ChoiceDTO createFullChoiceDTO() {
        return new ChoiceDTO(
                1,
                "choice",
                true
        );
    }
}
