package com.example.mynt_finance_backend.util.commonDTOs;

import java.util.List;

public record ParentChildrenDTO<Parent, Child>(
        Parent parent,
        List<Child> children
) {
}