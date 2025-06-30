package com.github.bvschaik.reproducer.async;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.ConcurrencyManagement;
import jakarta.ejb.ConcurrencyManagementType;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class AsyncProcess {

    @PersistenceContext
    private EntityManager em;

    @Asynchronous
    public void doStuff() {
        em.persist(new SampleEntity("hello"));
    }
}
