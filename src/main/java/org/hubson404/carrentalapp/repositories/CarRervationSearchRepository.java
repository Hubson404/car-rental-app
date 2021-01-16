package org.hubson404.carrentalapp.repositories;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CarRervationSearchRepository {

  private final EntityManager entityManager;

  public void asd() {
    entityManager.getCriteriaBuilder();
    if (carId != null) {

    }
  }
}
