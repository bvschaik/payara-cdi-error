package com.github.bvschaik.reproducer.async;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.ConcurrencyManagement;
import jakarta.ejb.ConcurrencyManagementType;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class AsyncWrapper {

    @Inject
    private AsyncProcess asyncProcess;

    @Asynchronous
    public void doStuff() {
        asyncProcess.doStuff();
    }
}
