package com.example.mynt_finance_backend.util.baseInterfaces;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Mapper<DTO, Entity> {

     DTO mapToDTO(Entity entity);

     Entity mapToEntity(DTO dto);

     default <T, U> Set<U> getChildEntitySet(Set<T> childEntities, Function<T, U> mapper) {
          return childEntities == null || childEntities.isEmpty() ?
                  new HashSet<>() :
                  childEntities.stream().map(mapper).collect(Collectors.toSet());
     }

     default <T, U> Optional<U> getChildEntityId(T childEntity, Function<T, U> mapper) {
          return childEntity == null ?
          Optional.empty() :
          Optional.of(mapper.apply(childEntity));
     }

}
