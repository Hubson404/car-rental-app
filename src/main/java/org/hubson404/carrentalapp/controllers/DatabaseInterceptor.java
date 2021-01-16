package org.hubson404.carrentalapp.controllers;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInterceptor extends EmptyInterceptor {

//  @Override
//  public boolean onSave(final Object entity, final Serializable id, final Object[] state, final String[] propertyNames, final Type[] types) {
//    CarReservation carReservation = (CarReservation) entity;
//    propertyNames["creationTimestamp"] = 2;
//    state[2] = LocalDateTime.now();
//    return true; // tak, zmodyfikowałem/ał encje w tej metodzi
//  }
//
//  @Override
//  public boolean onFlushDirty(final Object entity, final Serializable id, final Object[] currentState, final Object[] previousState,
//                              final String[] propertyNames, final Type[] types) {
//    return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
//  }
}
