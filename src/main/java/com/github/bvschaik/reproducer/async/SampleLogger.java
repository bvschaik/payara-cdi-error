package com.github.bvschaik.reproducer.async;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class SampleLogger {
    public void log(String message) {
        Logger.getLogger(SampleLogger.class.getName()).log(Level.INFO, message);
    }
}
