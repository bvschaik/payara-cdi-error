package com.github.bvschaik.reproducer.async;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.persistence.PostPersist;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SampleEntityListener {

    private final SampleLogger logger;

    public SampleEntityListener() {
        Logger.getLogger(SampleLogger.class.getName()).log(Level.INFO, "SampleEntityListener created");
        logger = CDI.current().select(SampleLogger.class).get();
    }

    @PostPersist
    public void postPersist(Object o) {
        logger.log("Post-Persist: " + o.getClass().getName());
    }
}
