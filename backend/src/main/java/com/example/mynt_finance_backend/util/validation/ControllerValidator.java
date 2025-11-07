package com.example.mynt_finance_backend.util.validation;

import com.example.mynt_finance_backend.errorHandling.exceptions.EntityDoesNotExistException;
import com.example.mynt_finance_backend.errorHandling.exceptions.ParentAlreadyContainsChildException;
import com.example.mynt_finance_backend.errorHandling.exceptions.ParentDoesNotContainChildException;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface ControllerValidator extends Validator {

    static <T> ControllerValidator exists(Predicate<T> predicate, T id) {
        return () -> {
            if (!predicate.test(id)) {
                throw new EntityDoesNotExistException("Resource with id=" + id + " does not exist");
            }
        };
    }

    static <T> ControllerValidator existsAll(Predicate<T> predicate, List<T> ids) {
        return () -> {
            for (T id : ids) {
                if (!predicate.test(id)) {
                    throw new EntityDoesNotExistException("Resource with id=" + id + "does not exist");
                }
            }
        };
    }

    // Only the hasChild(Integer parentId, Integer childId) methods should ever be passed as BiPredicates
    // o/w hasChild() and hasNoChild() methods below will not work
    static <T, U> ControllerValidator hasChild(BiPredicate<T, U> predicate, T parentId, U childId) {
        return () -> {
            if (!predicate.test(parentId, childId)) {
                throw new ParentDoesNotContainChildException("Parent resource with id=" + parentId + " has no child with id=" + childId);
            }
        };
    }

    static <T, U> ControllerValidator hasNoChild(BiPredicate<T, U> predicate, T parentId, U childId) {
        return () -> {
            if (predicate.test(parentId, childId)) {
                throw new ParentAlreadyContainsChildException("Parent resource with id=" + parentId + " already has child with id=" + childId);
            }
        };
    }

    // For situations where the child is dependent on the existence of the parent and the parent/child relationship is defined as @OneToOne
    static <T> ControllerValidator hasSingleDependent(Predicate<T> predicate, T parentId) {
        return () -> {
            if (!predicate.test(parentId)) {
                throw new ParentDoesNotContainChildException("Parent resource with id=" + parentId + " has no dependent child");
            }
        };
    }

    static <T> ControllerValidator hasNoSingleDependent(Predicate<T> predicate, T parentId) {
        return () -> {
            if (predicate.test(parentId)) {
                throw new ParentAlreadyContainsChildException("Parent resource with id=" + parentId + " already has single dependent child");
            }
        };
    }

    default ControllerValidator and(ControllerValidator other) {
        return () -> {
            this.validate();
            other.validate();
        };
    }
}